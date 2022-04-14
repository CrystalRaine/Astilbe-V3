# Astilbe-V3
Java Note Taking application, 3rd version<br><br>

use text entry line in upper left to name created notes<br>
commands in text line: 
 - delete [filename]
   - removes the given file
   - removes all links to the file
 - save
   - saves all changes
   - application autosaves frequently and on close however this forces a save
 - hide [filename]
   - hides the given file from view
   - will no longer be deleted with standard delete command
   - it is still removable with "delete [filename].inv"

Filetypes: 
- Text Document
  - plaintext document
- Folder
  - a folder that can contain other files
- Split folder 
  - folder that can contain other files
  - displays two of those at once
  - split horizontally
- PNG Image
  - holds an image selected from the filesystem
- Truth Table
  - a truth table generator
  - generates truth tables given a binary expression
- Vertical Split Folder
  - folder that can display other files
  - displays two at once
  - split vertically
- Chess
  - playable chessboard
  - currently incomplete/in progress
- Assignment Tracker
  - assignment tracking page
  - add / remove assignments
  - adds date
- Link
  - link to another file
  - allows a file to show in two locations
  - does not allow recursion
