# User Guide
[//]: # (@@author A0139401N)
* [About Us](#about-us)
* [Overview](#overview)
* [Getting Started](#getting-started)
* [Features](#features)
    * [Help](#viewing-help--help)
    * [Add task](#adding-a-task--add)
    * [Edit task](#editing-a-task--edit)
    * [Mark task](#mark-task-as-done--mark)
    * [Unmark task](#unmark-task-as-done--unmark)
    * [List task](#listing-tasks-in-a-certain-category--list)
    * [Find task](#finding-all-tasks-containing-any-keyword-in-their-name--find)
    * [View task](#view-a-task--view)
    * [Delete task](#deleting-a-task--delete)
    * [Undo](#undo-the-most-recent-operation--undo)
    * [Redo](#redo-the-most-recent-operation--redo)
    * [Task Due](#find-all-tasks-due--taskdue)
    * [Save To](#changing-your-save-location--saveto)
    * [Clear](#clearing-all-the-tasks--clear)
    * [Automatic Save](#automatic-save)
    * [Exit](#exiting-the-program--exit)
* [FAQ](#faq)
* [Command Summary](#command-summary)
[//]: # (@@author)

[//]: # (@@author A0139401N)
## About Us
Living in the modern and fast-paced world nowadays, we are constantly overwhelmed with tasks to do. Many people face the problem of time management as the traditional methods prove to be little effective. That is what Do-er List aims to solve.

Do-er List is a task manager that is designed for students and office workers. It is a beginner-friendly desktop program that aids you in the planning and completion of your daily tasks.  It does not matter if you are planning a big birthday surprise event or recurring task of handling the laundry every now and then, Do-er List is here to resolve your problems.

This user guide aims to allow any user to seamlessly use the product, as intended to. Just follow the instructions as stated and you will get the results you desire.

Eager and excited? Then let us proceed!

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139401N)
## Overview

<img src="../docs/images/UI_Guide/demo_labels.png" width="800">

| Labels | Description |
|-------- | -------- |
|1 | Type your commands in the **Command Console** to execute the desired commands. |
|2 | **Feedback Console** shows if your command is executed properly or not. |
|3 | View your default categories in the **Default Panel**. |
|4 | **Tasks Panel** will display all the tasks you listed in a panel. |
|5 | **Category Panel** shows all the custom categories that you have created. |
|6 | View your last update and file storage in **Update Status**.|


## Getting Started

Do-er List makes the process of adding, editing or deleting your tasks a seamless process. Long gone are the days when you have to type in long and complicated commands or endlessly click on multiple buttons to get what you desire.

All commands have this standard format:

`Command required_fields [optional_fields]...`

All commands start with a command words, followed by fields that are replaced by your inputs. The fields in the square bracket "[" and "] are optional. You can choose to not include these fields.
<br>
*Please take note of the following:*
[//]: # (@@author)

[//]: # (@@author A0139401N)
1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > This app will not work with earlier versions of Java 8.<br>
2. Download the latest `doerlist.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for the Do-*er*List.
4. Double-click the file to start the app. The GUI should appear in a few seconds. <br>
<img src="../docs/images/UI_Guide/welcome.png" width="600"><br>
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.
   *  e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the User Guide in a new window.
6. Some example commands you can try:
   * **`add`**` /t Do post-lecture quiz /s today 10:00 /e tomorrow 12:00 /c CS2103` :
     adds a task called `Do post-lecture quiz` to the Do-*er*List that starts `today` at `10:00`
	 and ends` tomorrow` at `13:00`  under the category `CS2103`.
	* **`list`**` CS2103` : lists all tasks in **CS2103**
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the application
7. Refer to the [Features](#features) section below for details of each command.<br>

[Back To Top](#user-guide)

## Features

> **Command Format**
>
> * Words in **`UPPER_CASE`** are the parameters.
> * Items in **`SQUARE_BRACKETS`** are optional.
> * Items with **`...`** after them can have multiple instances.
> * If a command has multiple parameters, the order of parameters doesn't matter.
[//]: # (@@author)

[//]: # (@@author A0139401N)
#### Viewing help : `help`
Format: `help` or `help [COMMAND_NAME]`

<img src="../docs/images/UI_Guide/help_command.png" width="600">
> If the `COMMAND_NAME` is supplied, it will display the instructions of using that command.<br>
> Help is also shown if you enter an incorrect command e.g. `abcd`

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139401N)
#### Adding a task : `add`
Adds a task to the Do-*er*List<br>
Format: `add /t TITLE [/d DESCRIPTION] [/s START] [/e END] [/c CATEGORY] [/r RECURRING]...`

<img src="../docs/images/UI_Guide/add_command.png" width="600">
> * You can choose to have no categories or any number of them in your task.
> * The **`START`** or **`END`** parameter supports natural language input.
> * **`RECURRING`** intervals can also be added in and also supports in natural language input.
> * If the **`START`** date is missing, the Do*er*-List set it to its current timing and date by default.
> * If there is no **`START`** or **`END`** parameters, the Do*er*-list will create task without start date and end date and move it to **`Inbox`** built-in category.
> * Supported list for natural language for **`START`** and **`END`**:
>  * next X hours / days / weeks / months
>    * *X* can be any number: 1, 2, 3, ...
>  * today
>  * tomorrow
>  * next week / month
> * Supported list for natural language for **`RECURRING`** tasks:
>  * daily
>  * weekly
>  * monthly
>  * yearly

Examples:

* `add /t Weekly Laundry /s 2016-11-23 21:00 /c Chores`<br>
* `add /t Daily Exercise and Workout!`<br>
* `add /t Call Mum in Hanoi /d Limit chat timing for overseas charges /s tomorrow 8pm /e tomorrow 10pm /c Optional`
* `add /t Check Email /s tomorrow 8pm /e tomorrow 10pm /r daily`

[//]: # (@@author)

[Back To Top](#user-guide)

[//]: # (@@author A0139401N)
#### Editing a task : `edit`
Edit an existing task in the Do-*er* List<br>
Format: `edit INDEX [/t TITLE] [/d DESCRIPTION] [/s START] [/e END] [/c CATEGORY] [/r RECURRING]...`

<img src="../docs/images/UI_Guide/edit_command.png" width="600">
> Edit an existing task by calling its index. The task's title, description, start date, end date, recurring intervals and categories can be edited.

Examples:

* `edit 2 /t Do the Laundry /c Chores /r Daily`
* `edit 3 /c Do Homework`
* `edit 3 /s tomorrow 23 00`

> Attributes that are not supplied will not be updated


[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139401N)
#### Mark task as done : `mark`
Marks a certain task as done in the Do*er*-list.<br>
Format: `mark TASK_NUMBER`

<img src="../docs/images/UI_Guide/mark_command.png" width="600">
> Mark task `TASK_NUMBER` as done. The task must exist in the Do*er*-list.


Examples:

* `mark 5`
	* Returns task number `5` as done.
	* If a recurring task is mark is done, it will automatically update its start and end date.

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139401N)
#### Unmark task as done : `unmark`
Marks a certain task as undone in the Do*er*-list.<br>
Format: `unmark TASK_NUMBER`

<img src="../docs/images/UI_Guide/unmark_command.png" width="600">
> Mark task `TASK_NUMBER` as undone. The task must exist in the Do*er*-list.

Examples:

* `unmark 5`
 	* Returns task number `5` as undone.
 	* If a recurring task is mark is unmarked, it will automatically subtract its start and end date by its given recurring interval.

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0147978E)
#### Listing tasks in a certain category : `list`
Shows a list of all tasks in the Do*er*-list under the specific category.<br>
Format: `list [CATEGORY]`

<img src="../docs/images/UI_Guide/list_command.png" width="600">
> * If the `CATEGORY` or `DATE` parameter is not supplied, then it will list all tasks.
> * Some default categories: `All` `Today` `Next` `Inbox` and `Complete` can be also listed.

Examples:

* `list`
* `list Friends`
* `list today`
* `list next`

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0147978E)
#### Finding all tasks containing any keyword in their name : `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

<img src="../docs/images/UI_Guide/find_command.png" width="600">
> * The search is not case sensitive.
>   * `lecture` will match `LecTure`
> * The order of the keywords does not matter. 
>  * `go to lecture` will match `Lecture go to`
> * Title and Description is searched.
> * All data in the Do-*er*List matching at least one keyword will be returned 
    * `lecture` will match `have lecture`

Examples: 

* `find david`<br>
  Returns `Drinks with David `

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0147978E)
#### View a task : `view`

Views the task identified by the index number used in the task panel.<br>
Format: `view INDEX`

<img src="../docs/images/UI_Guide/view_command.png" width="600">
> * Views the details of the task at the specified `INDEX`. 
> * The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 

* `list`<br>
  `view 2`<br>
  Views the 2nd task in the Do*er*-list.
* `find David` <br> 
  `view 1`<br>
  Views the 1st task in the results of the `find` command.
[//]: # (@@author)

[//]: # (@@author A0147978E)
[Back To Top](#user-guide)

#### Deleting a task : `delete`
Deletes the specified task / event from the Do-*er*List.<br>
Format: `delete INDEX`

<img src="../docs/images/UI_Guide/delete_command.png" width="600">
> * Deletes the task / event at the specified `INDEX`. 
> * The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task / event in the Do-*er*List. <br>

* `find David`<br>
  `delete 1`<br>
  Deletes the 1st task / event in the results displayed by using the `find` command.

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139168W)
#### Undo the most recent operation : `undo`
Undo the most recent operation which modify the data in the Do*er*-list<br>
Format: `undo`

<img src="../docs/images/UI_Guide/undo_command.png" width="600">

[Back To Top](#user-guide)

#### Redo the most recent operation : `redo`
Redo the most recent undo<br>
Format: `redo`

<img src="../docs/images/UI_Guide/redo_command.png" width="600">
> An `undo` command must first be used before `redo` command can be executed.

[Back To Top](#user-guide)

[//]: # (@@author A0140905M)
#### Find all tasks due : `taskdue`
Finds all tasks due on and before the date specified in the Do*er*-list.<br>
Format: `taskdue END_DATE`

<img src="../docs/images/UI_Guide/taskdue_command.png" width="600">
> * Finds all tasks due on and before `END_DATE`.<br>
> * The date can be in natural language*.
<br>
> * Supported list for natural language*:
>  * next X hours / days / weeks / months
>    * *X* can be any number: 1, 2, 3, ...
>  * today
>  * tomorrow
>  * next week / month

Examples:

* `taskdue tomorrow`
* `taskdue next 5 hours`
* `taskdue 2016-11-11 21:03`

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0140905M)

#### Changing your save location : `saveto`
Save the data into a new file and location<br>
Format: `saveto NEW_LOCATION`

<img src="../docs/images/UI_Guide/saveto_command.png" width="600">

Examples:

* `saveto data/newsampledata.xml`

[Back To Top](#user-guide)

#### Clearing all the tasks : `clear`
Clearing all the tasks<br>
Format: `clear`

<img src="../docs/images/UI_Guide/clear_command.png" width="600">

[Back To Top](#user-guide)
[//]: # (@@author)

[//]: # (@@author A0139401N)
#### Automatic save
The Do*er*-list data are saved in the hard disk automatically after any command that changes the data.<br>

[Back To Top](#user-guide)

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`

[Back To Top](#user-guide)
[//]: # (@@author)


[//]: # (@@author A0139401N)
## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Do-*er* List folder.
<br>
<br>

**Q**: Running "doerlist.jar" gives and error or does not seem to work.<br>
**A**: You will need to install the latest version of Java. Refer to the installation guide [here](https://java.com/en/download/help/download_options.xml).
<br>
<br>

**Q**: Where is the save button for me to save my schedule in this program? <br>
**A**: Your data are saved in the hard disk automatically after any command that changes the data as aforementioned [here](#automatic-save) in the guide. There is no need for you to save it manually.
<br>
<br>


## Command Summary

Command | Format
-------- | :--------
Help | `help [COMMAND]`
Add | `add /t TITLE [/d DESCRIPTION] [/s START] [/e END] [/c CATEGORY] [/r RECURRING] ...`
Edit | `edit INDEX [/t TITLE] [/d DESCRIPTION] [/s START] [/e END] [/c CATEGORY] [/r RECURRING] ...`
Mark Done | `mark TASK_NUMBER`
Mark Undone | `unmark TASK_NUMBER`
List | `list [CATEGORY]`
Find | `find KEYWORD [MORE_KEYWORDS]`
View | `view INDEX`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Task Due | `taskdue END_DATE`
Save | `saveto NEW_LOCATION`
Clear | `clear`
Exit | `exit`
[//]: # (@@author)