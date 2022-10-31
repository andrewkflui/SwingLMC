# Swing LMC: Little Man Computer with Interactive Features

A Java Swing implementation of the Little Man Computer (LMC), a popular tool for teaching computer architecture.

First developed in 2007

Copyright (C) 2007 - Andrew Kwok-Fai Lui

The Open University of Hong Kong

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not, see http://www.gnu.org/licenses/.

## Introduction

The LMC is a popular tool for teaching computer architecture created by [Dr. Stuart Madnick](https://en.wikipedia.org/wiki/Stuart_Madnick) in 1965. Refer to the Wikipedia page [Little Man Computer](https://en.wikipedia.org/wiki/Little_man_computer) for the details.

## Installation and Execution
### Pre-requisites
* Java JDK or JRE 1.8 or above

### Instruction
1. Download the repository to a folder, assuming that it is `/app/SwingLMC`. The Java sources are in the `src` folder and the classes are found in the `bin` folder.
2. Execute `SwingLMC.class` insider the folder

> `cd /app/SwingLMC`
> 
> `java -cp "./bin" SwingLMC`

### Screenshot

<img width="1000" alt="Screenshot 2022-10-31 at 12 20 11 PM" src="https://user-images.githubusercontent.com/8808539/198931395-535f70d5-7884-4945-8759-66f71fd5a94b.png">

Notes:
* The editor pane on the left allows LMC program editing. You may also load a program file, and save the edited program as a file.
* The memory pane is on the top-left. It will be filled with data after you __Assemble__ the code (under the __Tool__ menu item on the menubar).  The memory pane can be edited.
* The process status pane shows the registers.
* The memory pane will highlight the current execution address. Select __Execute Slowly__ to see it more clearly.

## Extension to the LMC

The Assembler supports logical address labeling. An address label, which is a single uppercase letter, may be defined for arbitrary addressing. The following program shows the use of label __B__.  

The purpose is to ease programming effort of students during development. After testing the program, the students may be asked to convert it to real addresses.

```
00  IN      ; input first number
01  STO 97  ; store first number
02  IN      ; input second number
03  STO 98  ; store second number
04  SUB 97
05  BRP B   ; 98 > 97
06  LDA 97   ; 97 > 98 so swap 97 and 98
07  STO 96
08  LDA 98
09  STO 97
10  LDA 96
11  STO 98
B : LDA 97
    OUT
    LDA 98
    OUT
    HLT
```
