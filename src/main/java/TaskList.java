import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * TaskList manages the list of tasks and implements the iterable interface
 */
public class TaskList implements Iterable<Task> {

    public ArrayList<Task> ListOfItems;
    public String line = "____________________________________________________________";
    TaskList() {
        this.ListOfItems = new ArrayList<>();
    }

    /**
     * adds a task to the list of items
     * @param item task that is being added
     * @return String output stating its has been added
     */
    public String add(Task item) {
        this.ListOfItems.add(item);

        return String.format("\nGot it. I've added this task:\n  %s\nNow you have %d tasks in your list.\n",
                item.toString(),
                this.ListOfItems.size());
    }

    /**
     * marks a task in the list with [✓] to state it is done
     * @param index position of the task to be marked in the list
     * @return String output stating that the task has been marked completed
     * @throws DukeException if the number given is not on the list
     */
    public String markCompleted(int index) throws DukeException {
        try {
            Task item = ListOfItems.get(index);
            item.markAsDone();

            return String.format("\nNice! I've marked this task as done:\n  %s\n", item.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("List does not contain the number specified");
        }
    }

    /**
     * removes a task form the list at the specified position
     * @param index
     * @return String output stating that the task has been removed
     * @throws DukeException if the number given is not on the list
     */
    public String deleteTask(int index) throws DukeException {
        try {
            Task item = ListOfItems.remove(index);

            return String.format("\nNoted. I've removed this task:\n  %s\nNow you have %d tasks in your list.\n",
                    item.toString(),
                    this.ListOfItems.size());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("List does not contain the number specified");
        }
    }

    /**
     * prints out the entire list
     * @return String output of the entire list
     */
    public String printOutList() {
        String list = "\nHere are the tasks in your list:\n";
        for (int i = 0; i < this.ListOfItems.size(); i++) {
            list += String.format("%d.%s\n", i + 1, this.ListOfItems.get(i).toString());
        }
        return list;
    }

    /**
     * iterates over the list of items
     * @return iterator with generic T as Task
     */
    @Override
    public Iterator<Task> iterator() {
        return this.ListOfItems.iterator();
    }

}
