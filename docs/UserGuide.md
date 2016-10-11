# User Guide

* [About Us](#about-us)
* [Overiew](#overview)
* [Getting Started](#getting-started)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## About Us
**Do-er List** is a beginner-friendly desktop program that aids you in the planning and completion of your daily tasks. Featuring an intuitive display and keyboard driven inputs to operate our program, we have done away the need of a mouse so you can schedule your tasks on the fly.
Be-gone with the woes of trawling through clunky interfaces of other scheduling apps to find those the buttons with your unresponsive touch-pad while waiting for the bus!

*Just type it in, hit* `enter`, *and your commands will be executed as you desired!*


## Overview

<img src="../docs/images/UI_Guide/demo_labels.png" width="800">

| Labels | Description |
|-------- | -------- |
|1 | **Command Console** enables you to input and execute your commands|
|2 | **Feedback Console** shows if your command is executed properly or not |
|3 | **Deafault Panel** contains all of your default categories |
|4 | **Tasks Panel** will display all the taks you listed in a panel|
|5 | **Category Panel** shows all the custom categories that you have created|
|6 | **Update Status** bar will indicate the file storage directory and your last update|


## Getting Started

1. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > This app will not work with earlier versions of Java 8.<br>
   
2. Download the latest `doerlist.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for the Do-*er*List.
4. Double-click the file to start the app. The GUI should appear in a few seconds. 
<br>
<img src="../docs/images/UI_Guide/welcome.png" width="600"><br>
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it.
   *  e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
6. Some example commands you can try:
   * **`add`**` -t Do post-lecture quiz <today->tomorrow> -c CS2103` :
     adds a task called `Do post-lecture quiz` to the Do-*er*List that starts `today`
	 and ends` tomorrow` under the category `CS2103`.
	* **`list`**` CS2103` : lists all tasks in **CS2103**
   * **`delete`**` 1` : deletes the 1st task shown in the current list
   * **`exit`** : exits the application
7. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> 
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * If a command has multiple parameters, the order of parameters doesn't matter.

#### Viewing help : `help`
Format: `help` or `help [COMMAND_NAME]`

<img src="../docs/images/UI_Guide/help_command.png" width="600">
> If the `COMMAND_NAME` is supplied, it will display the instructions of using that command.<br>
> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a task / an event: `add`
Adds a task/event to the Do-*er*List<br>
Format: `add -t TITLE [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...`

<img src="../docs/images/UI_Guide/add_command.png" width="600">
> * Task can have any number of categories (including 0)
> * The START or END parameter can be in natural language (next X hours, today, 
  tomorrow, next X days, next week, next month) or in standard format “2016-10-3 10:00”
> * If the START date is missing, the Do*er*-List set it to today by default.
> * If the there is not START->END parameters or END date is missing, the Do*er*-list will create task without start date and end date and move it to `Inbox` build-in category.

Examples: 

* `add -t Do post-lecture quiz {today->tomorrow} -c CS2103`<br>
* `add -t Do CA1 -d Oral Presentation {->next 2 days} -c CS2101`<br>
* `add {2016-10-4 10:00->2016-10-4 12:00} -t Take lecture -c CS2102`

#### Editing an event : `edit`
Edit an existing task / event in the Do-*er* List<br>
Format: `edit INDEX [-n TITLE] [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...`

<img src="../docs/images/UI_Guide/edit_command.png" width="600">
> Edit an existing task by calling its index. The event's title, description, start date, end date and category can be edited.
  
Examples:

* `edit 1 -t Do ST2334 quiz -c ST2334`
* `edit 2 {->next 5 days}`

> Attributes that aren't supplied will not be updated 

#### Mark task as done : `mark`
Marks a certain task as done in the Do*er*-list.<br>
Format: `mark TASK_NUMBER`

<img src="../docs/images/UI_Guide/mark_command.png" width="600">
> Mark task `TASK_NUMBER` as done. The task must exist in the Do*er*-list.

Examples:

* `mark 5`
  <br>Returns task number `5` as done.

#### Unmark task as done : `unmark`
Marks a certain task as undone in the Do*er*-list.<br>
Format: `unmark TASK_NUMBER`

<img src="../docs/images/UI_Guide/unmark_command.png" width="600">
> Mark task `TASK_NUMBER` as undone. The task must exist in the Do*er*-list.

Examples:

* `unmark 5`
  <br>Returns task number `5` as undone.

#### Listing tasks in a certain category : `list`
Shows a list of all tasks in the Do*er*-list under the specific category.<br>
Format: `list [CATEGORY]`

<img src="../docs/images/UI_Guide/list_command.png" width="600">
> If the `CATEGORY` parameter is not supplied, then list all tasks.

Examples:

* `list`
* `list CS2101`

#### Finding all tasks / events containing any keyword in their name: `find`
Finds tasks / events whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

<img src="../docs/images/UI_Guide/find_command.png" width="600">
> * The search is not case sensitive. e.g `lecture` will match `LecTure`
> * The order of the keywords does not matter. e.g. `go to lecture` will match `Lecture go to`
> * Title and Description is searched.
> * All data in the Do-*er*List matching at least one keyword will be returned 
    (i.e. `OR` search).
    e.g. `lecture` will match `have lecture`

Examples: 

* `find david`<br>
  Returns `Drinks with David `
  
#### View a task : `view`
Views the task identified by the index number used in the last task listing.<br>
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

#### Deleting a task / an event : `delete`
Deletes the specified task / event from the Do-*er*List. Irreversible.<br>
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
  Deletes the 1st task / event in the results of the `find` command.


#### Undo the most recent operation : `undo`
Undo the most recent operation which modify the data in the Do*er*-list<br>
Format: `undo`

<img src="../docs/images/UI_Guide/undo_command.png" width="600">
#### Redo the most recent operation : `redo`
Redo the most recent undo<br>
Format: `redo`

<img src="../docs/images/UI_Guide/redo_command.png" width="600">

#### Find all tasks due : `taskdue`
Finds all tasks due on and before the date specified in the Do*er*-list.<br>
Format: `taskdue END_DATE`

<img src="../docs/images/UI_Guide/taskdue_command.png" width="600">
> Finds all tasks due on and before `END_DATE`.<br>
  The date can be in natural language
  (E.g. next X hours, today, tomorrow, next X days, next week, next month)
  or in standard format
  (E.g. 2016-10-3 10:00)
  
Examples:

* `taskdue tomorrow`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

### Saving the data 
The Do*er*-list data are saved in the hard disk automatically after any command that changes the data.<br>


## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Do-*er* List folder.
<br>

**Q**: Running "doerlist.jar" gives and error or does not seem to work.<br>
**A**: You will need to install the latest version of Java. Refer to the installation guide [here](https://java.com/en/download/help/download_options.xml). 
<br>

**Q**: Where is the save button for me to save my schedule in this program? <br>
**A**: Your data are saved in the hard disk automatically after any command that changes the data as aforementioned [here](#saving-the-data) in the guide. There is no need for you to save it manually.

       
## Command Summary

Command | Format  
-------- | :-------- 
Help | `help [COMMAND]`
Add | `add -t TITLE [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...`
Edit | `edit INDEX [-t TITLE] [-d DESCRIPTION] [{[START]->[END]}] [-c CATEGORY] ...`
Mark Done | `mark TASK_NUMBER`
Mark Undone | `unmark TASK_NUMBER`
List | `list [CATEGORY]`
Find | `find KEYWORD [MORE_KEYWORDS]`
View | `view INDEX`
Delete | `delete INDEX`
Undo | `undo`
Redo | `redo`
Task Due | `taskdue END_DATE`
