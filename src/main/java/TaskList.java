import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * TaskList manages the list of tasks and implements the iterable interface
 */
public class TaskList implements Iterable<Task> {


    public ArrayList<Task> listOfItems;


    public ArrayList<Task> ListOfKeyWordItems = new ArrayList<>();

    public String line = "____________________________________________________________";

    TaskList() {
        this.listOfItems = new ArrayList<>();
    }

    /**
     * adds a task to the list of items
     *
     * @param item task that is being added
     * @return String output stating its has been added
     */
    public String add(Task item) {
        this.listOfItems.add(item);

        return String.format("\nGot it. I've added this task:\n  %s\nNow you have %d tasks in your list.\n",
                item.toString(),
                this.listOfItems.size());
    }

    /**
     * marks a task in the list with [✓] to state it is done
     *
     * @param index position of the task to be marked in the list
     * @return String output stating that the task has been marked completed
     * @throws DukeException if the number given is not on the list
     */
    public String markCompleted(int index) throws DukeException {
        try {
            Task item = this.listOfItems.get(index);
            item.markAsDone();

            return String.format("\nNice! I've marked this task as done:\n  %s\n", item.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("List does not contain the number specified");
        }
    }

    /**
     * removes a task form the list at the specified position
     *
     * @param index
     * @return String output stating that the task has been removed
     * @throws DukeException if the number given is not on the list
     */
    public String deleteTask(int index) throws DukeException {
        try {
            Task item = this.listOfItems.remove(index);

            return String.format("\nNoted. I've removed this task:\n  %s\nNow you have %d tasks in your list.\n",
                    item.toString(),
                    this.listOfItems.size());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("List does not contain the number specified");
        }
    }

    public String rescheduleTask(String details) {
        if (details == null) {
            throw new DukeException("I need something to work with.");
        }
        try {
            String[] detailsArray = details.split("/to", 2);

            String indexString = detailsArray[0].trim();
            int index = Integer.parseInt(indexString) - 1;
            Task temp = this.listOfItems.get(index);
            String dateTimeString = detailsArray[1].trim();
            LocalDateTime dateTime = DateConverter.parseString(dateTimeString);

            if (temp instanceof Deadline) {
                Task item = new Deadline(temp.description, dateTime);
                if (temp.isDone) {
                    item.markAsDone();
                }

                this.listOfItems.set(index, item);
                return String.format("\nNoted. I have now rescheduled %s to :\n  %s\nYou still have %d tasks in your list.\n",
                        temp.toString(), item.toString(),
                        this.listOfItems.size());
            } else if (temp instanceof Event) {
                Task item = new Event(temp.description, dateTime);
                if (temp.isDone) {
                    item.markAsDone();
                }
                this.listOfItems.set(index, item);
                return String.format("\nNoted. I have now rescheduled %s to :\n  %s\nYou still have %d tasks in your list.\n",
                        temp.toString(), item.toString(),
                        this.listOfItems.size());
            } else {
                return (new DukeException("Unable to detect class")).toString();
            }
        } catch (Exception e) {
            return (new DukeException("Unable reschedule the specified Task")).toString();
        }
    }


    /**
     * prints out the entire list
     *
     * @return String output of the entire list
     */

    public void findTask(String Keyword) {
        ListOfKeyWordItems.clear();
        for (Task item : listOfItems) {
            if (item.toString().indexOf(Keyword) != -1) {
                ListOfKeyWordItems.add(item);
            } else {

            }
        }

    }

    public String printOutKeyWordList() {
        String list = "\nHere are the matching tasks in your list:\n";
        for (int i = 0; i < ListOfKeyWordItems.size(); i++) {
            list += String.format("%d.%s\n", i + 1, ListOfKeyWordItems.get(i).toString());
        }
        return list;
    }


    public String printOutList() {
        String list = "\nHere are the tasks in your list:\n";
        for (int i = 0; i < this.listOfItems.size(); i++) {
            list += String.format("%d.%s\n", i + 1, this.listOfItems.get(i).toString());
        }
        return list;
    }

    /**
     * iterates over the list of items
     *
     * @return iterator with generic T as Task
     */
    @Override
    public Iterator<Task> iterator() {
        return this.listOfItems.iterator();
    }

}
