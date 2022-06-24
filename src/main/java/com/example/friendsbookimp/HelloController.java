package com.example.friendsbookimp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HelloController {
    public TextField friendName;
    public TextField friendAddress;
    public TextField friendPhone;
    public Button btnAdd;
    public Button btnDelete;
    public ListView friendsList;
    public TextArea infoText;
    public ArrayList<String> files;
    public ChoiceBox cbFiles;
    public String defaultGroup;
    public String currentGroup;
    public Button btnCreate;
    public TextField newGroupName;
    public ArrayList<Friend> friends;
    public ArrayList<String> groups;
    public String allGroups;

    public void initialize() throws IOException {
        files = new ArrayList<>();
        defaultGroup = "All Friends";
        allGroups = "All Groups";
        currentGroup = defaultGroup;

        FileWriter fw = new FileWriter(currentGroup + ".txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.close();


        FileWriter fw1 = new FileWriter(allGroups + ".txt", true);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.close();

        groups = new ArrayList<>();
        groups.add(defaultGroup);
        FileReader fr = new FileReader(allGroups+".txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line=br.readLine()) != null) {
            groups.add(line);
        }
        br.close();

        friends = new ArrayList<>();
        FileReader fr1 = new FileReader(currentGroup+".txt");
        BufferedReader br1 = new BufferedReader(fr1);
        while((line=br1.readLine()) != null) {
            String name = line;
            String address = br1.readLine();
            String phone = br1.readLine();
            Friend friend = new Friend(name, address, phone);
            friends.add(friend);
        }
        br1.close();
        friendsList.getItems().clear();
        for(int i=0; i<friends.size(); i++) {
            friendsList.getItems().add(friends.get(i).getName());
        }

        cbFiles.setItems(FXCollections.observableArrayList(groups));
        cbFiles.setValue(defaultGroup);
        cbFiles.setOnAction((event) -> {
            infoText.setText("");
            int selectedIndex = cbFiles.getSelectionModel().getSelectedIndex();
            Object selectedItem = cbFiles.getSelectionModel().getSelectedItem();
            currentGroup = cbFiles.getValue().toString();
            friends = new ArrayList<>();
            try {
                FileReader fr2 = new FileReader(currentGroup+".txt");
                BufferedReader br2 = new BufferedReader(fr2);
                String line2;
                while((line2=br2.readLine()) != null) {
                    String name = line2;
                    String address = br2.readLine();
                    String phone = br2.readLine();
                    Friend friend = new Friend(name, address, phone);
                    friends.add(friend);
                }
                br2.close();
                friendsList.getItems().clear();
                for(int i=0; i<friends.size(); i++) {
                    friendsList.getItems().add(friends.get(i).getName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        // clear the infoText
        infoText.setText("");
        // get name, address and phone from the text fields
        String name = friendName.getText().trim();
        String address = friendAddress.getText().trim();
        String phone = friendPhone.getText().trim();
        // name, address and phone cannot be empty
        if(name.length()==0 || address.length()==0 || phone.length()==0) {
            infoText.setText("Error: Name, address and phone cannot be empty!");
        }
        // create a new friend
        else {
            // inform the addition
            infoText.setText("New friend " + name + " added to the " + currentGroup + " Group!");
            friendName.setText("");
            friendAddress.setText("");
            friendPhone.setText("");
            FileWriter fw = new FileWriter(currentGroup+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(name);
            bw.newLine();
            bw.write(address);
            bw.newLine();
            bw.write(phone);
            bw.newLine();
            bw.close();
            friendsList.getItems().add(name);
            Friend friend = new Friend(name, address, phone);
            if(friends==null) {
                friends = new ArrayList<>();
            }
            friends.add(friend);
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) throws IOException {
        // clear the infoText
        infoText.setText("");
        // check if a friend is selected
        if(friendsList.getSelectionModel().getSelectedItem()==null) {
            infoText.setText("Error: No friend is selected!");
        }
        // delete the friend from the friends book
        else {
            int index = friendsList.getFocusModel().getFocusedIndex();
            String name = friends.get(index).getName();
            friends.remove(index);
            friendsList.getItems().remove(index);
            infoText.setText("Friend " + name + " removed from the "+ currentGroup + " Group!");
            FileWriter fw = new FileWriter(currentGroup+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0; i<friends.size(); i++) {
                bw.write(friends.get(i).getName());
                bw.newLine();
                bw.write(friends.get(i).getAddress());
                bw.newLine();
                bw.write(friends.get(i).getPhone());
                bw.newLine();
            }
            bw.close();
        }
    }

    public void onListClick(MouseEvent mouseEvent) {
        if(friendsList.getSelectionModel().getSelectedItem()!=null) {
            int index = friendsList.getSelectionModel().getSelectedIndex();
            Friend friend = friends.get(index);
            infoText.setText(friend.toString());
        }
    }

    public void btnCreateOnClick(ActionEvent actionEvent) throws IOException {
        String group = newGroupName.getText();
        if(group.length()==0 || group.equals(defaultGroup) || group.equals(allGroups)) {
            infoText.setText("Invalid group name! Please try again!");
        }
        else if(!groups.contains(group)) {
            groups.add(group);
            cbFiles.getItems().add(group);
            FileWriter fw = new FileWriter(group+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.close();
            FileWriter fw1 = new FileWriter(allGroups+".txt", true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw1.write(group);
            bw1.newLine();
            bw1.close();
            infoText.setText("Group \"" + group + "\" has been created!");
            newGroupName.setText("");
        }
        else {
            infoText.setText("Group \"" + group + "\" already exists!");
        }
    }
}