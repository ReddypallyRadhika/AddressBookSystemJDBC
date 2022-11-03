package com.bridgeLabz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookDirectory implements AddressBookDirectoryIF{
    public AddressBook addressBook;
    Scanner scannerObject=new Scanner(System.in);
    Map<String,AddressBook> addressBookDirectory=new HashMap<>();

    @Override
    public void operationDirectory() {
        boolean moreChanges=true;
        do{
            System.out.println("\nChoose the operation on the directory you want to perform");
            System.out.println("1.Add on Address Book\n2.Edit Existing Address Book\n3.Search Person By Region\n4.View People By Region\n3.Count People By Region");
            switch (scannerObject.nextInt()) {
                case 1 -> addAddressBook();
                case 2 -> editAddressBook();
                case 3 -> {
                    System.out.println("Enter \n1.Search By City\n2.Search By State");
                    int searchChoice = scannerObject.nextInt();
                    if (searchChoice == 1) {
                        searchByCity();
                    } else {
                        searchByState();
                    }
                }
                case 4 -> {
                    System.out.println("Enter \n1.Display By City\n2.Display By State");
                    int displayChoice = scannerObject.nextInt();
                    if (displayChoice == 1) {
                        displayPeopleByRegion(AddressBook.personByCity);
                    } else {
                        displayPeopleByRegion(AddressBook.personByState);
                    }
                }
                case 5 -> {
                    System.out.println("Enter \n1.Display By City\n2.Display By State");
                    int countChoice = scannerObject.nextInt();
                    if (countChoice == 1) {
                        countPeopleByRegion(AddressBook.personByCity);
                    } else
                        countPeopleByRegion(AddressBook.personByState);
                }
                case 6 -> displayDirectoryContents();
                case 7 -> {
                    moreChanges = false;
                    System.out.println("Exiting Address Book Directory !");
                }
            }
        }while(moreChanges);
    }
    @Override
    public void addAddressBook() {
        System.out.println("Enter the name of the Address Book you want to add");
        String bookNameToAdd=scannerObject.next();
        if (addressBookDirectory.containsKey(bookNameToAdd)){
            System.out.println("Book Name Already Exists");

        }
        AddressBook  addressBook=new AddressBook();
        addressBook.operation();
        addressBook.setAddressBookName(bookNameToAdd);
        addressBookDirectory.put(bookNameToAdd,addressBook);
    }
    @Override
    public void editAddressBook() {
        System.out.println("Enter the name of the Address Book you want to edit");
        String addressBookToEdit=scannerObject.next();
        if (addressBookDirectory.containsKey(addressBookToEdit
        )) {
            addressBook = addressBookDirectory.get(addressBookToEdit);
            addressBook.operation();
        }
        else{
            System.out.println("Book Does Not Exists");

        }

    }
    @Override
    public void searchByCity(){
        System.out.println("Enter the name of the city where the person resides:");
        String cityName=scannerObject.next();
        System.out.println("Enter the name of the person:");
        String personName=scannerObject.next();
        for(AddressBook addressBook:addressBookDirectory.values()){
            ArrayList<ContactPerson> contactList=addressBook.getContact();
            contactList.stream().filter(person->person.getFirstName().equals(personName)&&person.getAddress().getCity().equals(cityName))
                    .forEach(person->System.out.println(person));
                                                                                                                                                       }
    }
    @Override
    public void searchByState(){
        System.out.println("Enter the name of the state where the person resides:");
        String stateName=scannerObject.next();
        System.out.println("Enter the name of the person:");
        String personName=scannerObject.next();
        for(AddressBook addressBook:addressBookDirectory.values()){
            ArrayList<ContactPerson> contactList=addressBook.getContact();
            contactList.stream().filter(person->person.getFirstName().equals(personName)&&person.getAddress().getCity().equals(stateName))
                    .forEach(person->System.out.println(person));
        }
    }
    @Override
    public void countPeopleByRegion(HashMap<String, ArrayList<ContactPerson>> listToDisplay){
        System.out.println("Enter the name of the region:");
        String regionName=scannerObject.next();
        long countPeople=listToDisplay.values().stream().map(region->region.stream()
                        .filter(person->person.getAddress().getState().equals(regionName)||
                                person.getAddress().getCity().equals(regionName)))
                .count();
        System.out.println("Number of People residing in"+regionName+" are: "+countPeople+"\n");
    }
    @Override
    public void displayPeopleByRegion(HashMap<String, ArrayList<ContactPerson>> listToDisplay){
        System.out.println("Enter the name of the region:");
        String regionName=scannerObject.next();
        listToDisplay.values().stream().map(region->region.stream()
                        .filter(person->person.getAddress().getState().equals(regionName)||person.getAddress()
                                .getCity().equals(regionName)))
                .forEach(person->person.forEach(personDetails->System.out.println(personDetails)));

    }
    @Override
    public void displayDirectoryContents(){
        System.out.println("----Contents of the Address Book Directory-----");
        for(String eachBookName:addressBookDirectory.keySet())
        {
            System.out.println(eachBookName);
        }
        System.out.println("---------------------------");
    }
}
