#### Load sample data
1. Run “doerList.jar”. 
2. For Windows users, if your computer is unable to run as a Java file, right-click the .jar file and change the Opens with: from "some program" to “Java™ Platform SE Binary”.
3. Click “apply” and “OK”
4. Enter “add /t test” into the command box. It should create the “data” folder. Close the application right after it processes that command.
5. Click the “data” folder. In the folder, click the “doerList.xml” file.
6. Now open the “SampleData.xml” file. Press “Ctrl” + “a” and then “Ctrl” + “c”.
7. Go to the “doerList.xml” file. Press “Ctrl” + “a” and then “Ctrl” + “v”.
8. Run “doerList.jar”. The application should display the sample data.

#### Test cases and brief overview
- The following test cases are to be done in sequential order to get the correct results and behaviours. The UI will also be explained.
- Do-er List's UI consists of a command console, a feedback panel, a default panel, a task panel, a category panel, and a status panel. Observe the change in each panel as the console executes the commands.
- The sample data some sample data already inside for you to look around and understand the UI better

#### help
Type:
`help`
to bring up the User Guide.

If you wish to only see the usage guide of a specific command, type `help COMMAND_NAME`

`help add`

brings up the details of `add` command in the console panel.

`help edit`

brings up the details of `edit` command in the console panel.

#### add
Add a floating task by `add /t task_name`

`add /t Learn Japanese`

A task titled "learn Japanese is added in the Do-er List at the end (task 63). Floating task can also be seen in the `Inbox` default panel on the left. In this case. `Learn Japanese` is task 8.

When a task is created successfully, the console panel shows the message: `New task added: task_name`

You can also specify more fields such as `description` by `/d`,`start_time` by `/s`, `end_time` by `/e`, `category` by `/c`

`add /t Revise Notes /d lecture note 10 /s today 10 pm /e tomorrow 10 pm /c CS3241`

A task with the above details is created. You can view this task in the `All` list, in the `Today` default panel or in `CS3241` category panel. Notice that our panel list is sorted by starting time, so depending on when you test, it should be in the correct position in the task list based on starting time.

Moreover, besides the title, other fields are optional. And the order of the fields do not matter. Let's try this:

`add /s today /d important event /t Visiting grandma`
`add /e this sunday 23:59 /t Write reflection essay` 

The task "Visiting grandma" is created and you can view it in the `Today` default panel."Write reflection essay" is also created. You can view this in the `Today` default panel if it is Sunday today or `Next` default panel otherwise. When starting time is not specified, Do-er List take the starting time as right now.

If a title is not present, there will be an error indicating in the feedback panel. For example, type:

`add /s today /e tomorrow /c Life`

Recurring task is also supported with `/r` and recurrence such as `daily`, `weekly`, `yearly`. Type:

`add /t Christmas /s 25 December 00:00 /e 25 December 23:59 /r yearly`

The task "Christmas" is created (task 23 in `Next` default panel) and we see the little blue circular arrow indicating this task recurs every year.

Other examples:

`add /d Positive attitude /t Improve yourself`

to add a floating task with `title` "Improve yourself" and `description` "Positive attitude"

`add /r daily /t Running /s tomorrow 6 am /e tomorrow 7pm`

to add a daily recurring task starting from tomorrow onwards titled "Running".

*Wrong command such as* `add wfsdf` *or* `add` *will prompt the users that the command is wrong in the feedback panel. This is true for any other commands and will be omitted from now on.*

#### edit
We can `edit` any field of a task we have created by providing the task's index in the current task panel. Let's go to the `All` default panel and type:

`edit 1 /t Submit Assignment 5`

The task title changes from "Submit Assignment 4" to "Submit Assignment 5".
Let's go to the `Inbox` default panel. Type

`edit 2 /e 2 days from now /c Reminders`

The floating task is removed from `Inbox` as it now has a due date. You can check in `Next` default panel or `Reminders` category panel. The feedback panel shows the details of the task before and after edited.

`edit 100`

will show an invalid command as the index 100 is greater than the our list size.

#### mark/unmark
mark and unmark take in the index of a task in the current task panel and mark it as done or mark a completed task as undone.

Let's go to our `All` default panel. We see a little red circle with a number. That is the number of task overdue. Let say we have finish assignment 5. Type

`mark 1`

"Summit Assignment 5" will be removed from Overdue and go to the `Complete` default panel or `Complete` at the end of `All` default panel. Notice that a completed task is grey in colour.

Let's go to `Today` default panel. Task 1 is a everyday recurring task "Go collect mail". Let's mark it

`mark 1`

it goes to task 2 as it means that we have mark it as done for 1 day. For recurring task, `mark` will increase the time to the next recurring time.

Unmark is the opposite of mark. Type

`umark 1`

Unmarking a recurring task will decrease the time to the previous recurring time. In this case, the task "Print"'s start time decresase from 10 September to 9 September.

Let's go to the `Complete` default category and type:

`unmark 1`

which unmarks our completed task. In this case, the task disappears from the `Complete` default panel and appears back as Overdue in our `All` panel.

`mark 100`
`unmark 100`

will both indicate an error in the feedback panel as it is bigger than our list's size

#### list
So far, we have been using our mouse to click on various panels. We can do that with our keyboard using the `list` command. Type

`list` 

will show all tasks in the `All` default panel.

We can also list anything in the default panel as well as any categories in the category panel. Type:

`list all `

will show all tasks in the `All` default panel.

`list inbox` 

will show all tasks in the `Inbox` default panel

`list CS3241`

will show all tasks in the category `CS3241`, this is also not case sensitive so you can type

`list cs3241`

`list Hello World`

will indicate an error in our feedback panel that such category does not exist.
#### find
To find something by keyworkd, use the command `find` with keyword. Type:

`find friends`

All tasks that have "friends" in the `title` or the `description` will be listed in the `All (filtered)` default panel. The feedback panel also shows how many tasks are found.

We can also specify several queries". Type:

`find friends assignment go`

All tasks that have the above keywords in their title or description will be listed. Note that queries are not case sensitive so `friends` or `FriEndS` will return the same answer.

`find FriEndS`

return 5 tasks, same as `find friends`
#### view
Command `view` let you see the description of a task. Let's start with 

`find exam`

to show the filtered list of all exams we have in the list. There are 4 of them. Now type

`view 1`

to see the description of the first exam.

`view 4`

to see the description of the 4th exam

`view 3`

to see the description of the second exam.

Moreover, you can use the up arrow `↑` and down arrow `↓` to quickly navigate between different tasks.

`view 100` 

The feedback panel will prompt an error as the index is greater than our list's size

#### taskdue
Command `taskdue` takes in a time and shows all the tasks that have deadlines before that specified time. Type:

`taskdue sunday 23:59`

to see all tasks due by this sunday. You can see Overdue tasks, tasks need to be done today, tomorrow until this sunday.

Other examples:

`taskdue 5 Dec 2016`

to show all tasks due by 5 December 2016

`taskdue now`

to show all overdue tasks.

`taskdue last year`

and 0 task will be listed. The feedback panel will also prompt this
#### delete
To delete a task, type `delete` and supply the task's index. Let's type

`list`

to go back to our task panel.

`delete 1`

will delete the first task "Submit Assignment 5"

`delete 10`

will delete the 10th task currently shown in the task list

`delete 100`

will show an error that the index is invalid as 100 is greater than our list's size
#### undo/redo
Undo the last operation with `undo` and redo it with `redo`. Let's try out these sequence of commands:

`add Swimming`

will add the floating task `Swimming`. It is the last task shown in `Inbox` default panel.

`undo`

will revert back the `add` command, thus deleting the task "Swimming".

`redo` 

will revert back the last `undo`, thus the task "Swimming" is added back in.

`redo`

Typing redo again will prompt user in the feedback panel that `redo` is not available because there is nothing to undo. `undo ` and `redo` can be called unlimited times but number of `redo` cannot be more than number of `undo`. 

Let's type these commands:

`list all` to list all tasks

`delete 1` to delete task 1 which is "Brother's Birthday"

`mark 2` to mark task 2 as done

`list today` to list tasks in `Today` default panel

`edit 3 /t Walking my dog` to edit the task 3 which is "Walking tracker"

Let's type these sequence of `undo` and `redo`
`undo` will revert back the last command, which is `edit`, "Walking my dog" back to "Walking tracker"

`redo` will revert back the last undo. It's back to "Walking my dog"

`undo` will revert back the last redo. It's back to "Walking tracker"

`undo` will revert back the 2nd last command. The completed task in `Completed` is unmarked. Notice that `undo` does not do anything to the command `list today` as it does not modify the data

`undo`. More undo will revert even more commands. Now the command `delete 1` is recovered. "Brother's Birthday"appears back in the task list.

#### saveto

saveto change the file location that is saved. Type

`saveto data/newsampledata.xml`

The data is saved in the new file in `data/newsampledata.xml`. And it will be the saving location from now on. The Do-er List's status panel located at the bottom of the app will reflect this new change.


#### exit

`exit`

will exit the application