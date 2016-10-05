package seedu.address.testutil;

import seedu.doerList.commons.exceptions.IllegalValueException;
import seedu.doerList.model.AddressBook;
import seedu.doerList.model.category.Category;
import seedu.doerList.model.task.Task;
import seedu.doerList.model.task.UniqueTaskList;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder(AddressBook addressBook){
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withTask(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(person);
        return this;
    }

    public AddressBookBuilder withCategory(String categoryName) throws IllegalValueException {
        addressBook.addCategory(new Category(categoryName));
        return this;
    }

    public AddressBook build(){
        return addressBook;
    }
}
