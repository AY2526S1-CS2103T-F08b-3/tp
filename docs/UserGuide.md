---
layout: page
title: User Guide
---

ConnectEd is a **desktop app for managing tutors and students, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ConnectEd can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar connected.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list tutors` : Lists all tutors (use `list students` to list all students).

   * `add student aaron /hp 91234567 /a Blk 30 Geylang Street 29, #06-40 /s mathematics /l 3 /p 20-30` : Adds a **student** with subject, level, and price range.
     (Example for tutor: `add tutor Mary /hp 98765432 /a Tampines Ave 1 /s english /l 2-5 /p 25-40`)

   * `delete t1` : Deletes the 1st **tutor** shown in the current list.  
     (Use `delete s2` to delete the 2nd **student**.)

   * `clear` : Deletes all entries.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Parameters & placeholders  
  Commands follow the below exactly:  
  • Add: `add <tutor/student> <name> /hp <phone> /a <address> /s <subject> /l <level> /p <min-max>`  
  • List: `list <tutors/students>`  
  • Find: `find <tutor/student> /<field> <filter_value>` where `<field>` is `/s`, `/l`, or `/p`  
  • Match/Unmatch: `match t<INDEX> s<INDEX>` · `unmatch t<INDEX>` or `unmatch s<INDEX>` 


* Accepted values (validators)  
  • `<tutor/student>`: exactly “tutor” or “student” (case-insensitive).  
  • `<tutors/students>` for `list`: exactly “tutors” or “students” (case-insensitive).  
  • `/hp` phone: 8 digits (spaces allowed), digits only.  
  • `/s` subject: one of {english, mathematics, chinese, science} (case-insensitive).  
  • `/l` level:  
  – Student: single integer 1–6  
  – Tutor: single integer 1–6 **or** range `x-y` (1–6 to 1–6; no internal spaces)  
  • `/p` price range: `min-max`, integers 1–200.  


* Typed indices  
  • Use `t<INDEX>` for tutors and `s<INDEX>` for students (1-indexed; visible list indices; no spaces).  
  • Examples: `delete t1`, `delete s2`, `match t1 s2`, `unmatch s2`.  

* Errors (shape & value)  
  The app guides with specific messages on: wrong command format, missing/invalid `<tutor/student>` or `<tutors/students>`, invalid field (`/s` `/l` `/p`), malformed values (non-integer level, bad `min-max`, min>max), duplicates, and empty lists.  


* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.


* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a tutor or student: `add`

Adds a **tutor** or **student** with subject, level, and price range.

Format: `add <tutor/student> <name> /hp <phone> /a <address> /s <subject> /l <level> /p <min-max>`



Examples:
* `add student aaron /hp 91234567 /a Blk 30 Geylang Street 29, #06-40 /s mathematics /l 3 /p 20-30`  
* `add tutor Mary /hp 98765432 /a Tampines Ave 1 /s english /l 2-5 /p 25-40`
### Listing all persons : `list`

Shows a list of either tutors or students.

Format: `list tutors`  |  `list students`

Examples: `list tutors` , `list students`




### Finding student/tutor: `find`

Returns a filtered list of students/tutors from our database based on one condition (subject, level, or price).

Format: `find <tutor/student> /<field> <filter_value>`
* `<tutor/student>`: specifies whether to search tutors or students.
* `<field>`: must be one of the following keywords:
  * `/s`:  subject
  * `/l`:  level
  * `/p`:  price range
* `<filter_value>`:  the keyword/number/range to match against the chosen field.

Parameter specifications:
* Acceptable values:
  * `<tutor/student>` must be exactly tutor or student (case insensitive).
  * `<field>` must be exactly `/s (subject)`, `/l (level 1–6)`, or `/p (price range)`.
  * `<filter_value>` must match the field:
    * `/s <subject>`:  subject keyword (e.g., mathematics, english, chinese, science)
    * `/l <level>`: integer from 1–6 to simulate primary school p1 - p6.
    * `/p <range>`:  two integers separated by a dash (e.g., 10-20).

Examples:
* `find Tutor /s mathematics` returns `tutors` with `mathematics` as their subject.
* `find tutor /l 3` returns `tutors` that teach children at level `3`.
* `find Student /p 10-20` returns `students` that will accept the price range of `10-20`.
  ![result for 'find tutor /s mathematics'](images/FindTutorResult.png)

### Match/Unmatch a student and a tutor : `match/unmatch`

Link one Tutor and one Student so both are flagged as Matched and hold a bidirectional reference to each other. 
Supports undoing via unmatch.

Format:
* Match: `match <ID of Person 1> <ID of Person 2> (Order of student teacher does not matter)`
* Unmatch: `unmatch <ID of Person> (Only require index of one of the matched pair)`
* Matches the student and tutor at the specified `ID`.
* Unmatch the student/tutor at the specified `ID` and its corresponding matched tutor/student.
* The id refers to the unique id number associated with each student and tutor.
* The id **must be a positive integer** 1, 2, 3, …​

Examples:
* `match 1 2` matches the tutor with id of #1 with the student with the id of #2 in the list.
* `unmatch 1` unmatch the tutor with id of #1 with its corresponding matched student.
  ![result for 'match t1 s1'](images/MatchResult.png)

### Deleting a person : `delete`

Deletes a person (student/tutor) from the ConnectEd database

Format:
* Delete tutor: `delete t<INDEX>`
* Delete student: `delete s<INDEX>`
* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed tutor/student list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `delete t1` deletes the 1st tutor in the tutor list.
* `delete s2` deletes the 2nd student in the student list.

### Clearing all entries : `clear`

Clears all entries from the ConnectEd.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ConnectEd data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ConnectEd data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, ConnectEd will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ConnectEd to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** |`add <tutor/student> <name> /hp <phone> /a <address> /s <subject> /l <level_or_range> /p <min-max>` e.g., `add student aaron /hp 91234567 /a Blk 30 Geylang Street 29, #06-40 /s mathematics /l 3 /p 20-30`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Find** | `find <tutor/student> /<field> <filter_value>`<br> e.g., `find student /s chinese`
**Match/Unmatch** | `match <Id> <Id> / unmatch <Id> `<br> e.g., `match 1 2 / unmatch 1`
**List** | `list students / list tutors`
**Help** | `help`
