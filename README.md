# MTool - Mutation testing tool for RAPID

The tool is designed to generate mutants in a RAPID program given as inputs. It supports a variety of unit level and task level mutations.
### Unit level Mutations
* Operator Replacement - Supports AOR, ROR, LOR
* Assignment Statement Mutation - Replace values following an Assignment (:=) operator
* Conditional Statement Replacement - Replace conditionals with user input
* RAPID SPECIFIC MUTATIONS
    + Move Argument Replacement - Modify “To point”, “speed”, “zone”, “tool”, “Coordinate system”
    + Robtarget Value Replacement - Modify position (x, y, z) and orientation (q1, q2, q3, q3) values.


### Task level Mutations
* Wait Until/Test and Set
  + Change variable
  + Move statement to another line
  + Toggle Test and Set
* Set/Wait Do
  + Change digital output
  + Change value
  + Toggle set/wait
  + Move statement to another line
* Set Do Isignal Do
  + Change digital output
  + Change value
  + Change interrupt variable
  + Move statement to another line
* WaitSyncTask/SyncMoveOn
  + Change identity point
  + Change task

---
### Command to run
```
java -jar MTool.jar \path\to\input_program.txt
```




