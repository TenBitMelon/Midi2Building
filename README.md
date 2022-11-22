# Midi2Building
This is the generator branch for the Midi2Building project.\
The UI and combined project are under the main branch but to keep files clean the generator is coded separately then combined.

## File Structure
```text
┣ DataPack
┃ ┣ output              : String                        // Output file location
┃ ┣ folderName          : String                        // DataPack Folder name
┃ ┣ namespace           : String                        // DataPack namespace
┃ ┣ events              : ArrayList<NoteEvent>          // Events to be converted into functions
┃ ┣ largestTime         : long                          // Value in ticks used to create actionbar timeline
┃ ┣ size                : Schematic.Location            // Size of schematic used for clear function
┃ ┗ generate()          : void                          // Creates the files/ folders for the DataPack
┃                                                       // Calls all of the events to generate their files
┣ Main  
┃ ┣ datapackOutput      : String                        // Reads from resourceLocations.txt file for dataPack output
┃ ┣ song                : File                          // File of the midi track used
┃ ┣ nbt                 : File                          // File of the structure file use
┃ ┣ noteToSound         : HashMap<String, SoundAtlas>   // Sample data that would be provided by the gui
┃ ┗ main()              : void                          // Creates a MidiParser
┃                                                       // Creates a Schematic from the nbt
┃                                                       // Creates a DataPack provided the output location
┃                                                       // Loops over the notes and generates events based on the associated sound.
┃                                                       // Finalizes and generates the datapack
┣ MidiParser    
┃ ┣ NOTE_ON             : int                           // Hardcoded bytes of NOTE ON in the midi
┃ ┣ NOTE_OFF            : int                           // Hardcoded bytes of NOTE OFF in the midi
┃ ┣ NOTE_NAMES          : String[]                      // String values of notes to use in the HashMap
┃ ┃                                                     ?? Can this be left as ints and then int in the hashmap multiplies times octave
┃ ┣ midiFile            : File                          // Provided midi file
┃ ┣ sequence            : Sequence                      // Midi sequence from file
┃ ┣ noteCounts          : HashMap<String, Integer>      // HashMap for debuging the amount of each note
┃ ┣ notes               : ArrayList<Note>               // List of all notes in the song
┃ ┗ MidiParser()        : MidiParser                    // Constructor of MidiParser
┃                                                       // Gets the sequence
┃                                                       // Loops over the tracks
┃                                                       // Loops over the "messages" in each track
┃                                                       // Gathers key, octave, note from "message"
┃                                                       // Creates a Note object and adds it to the list
┣ Note  
┃ ┣ time                : long                          // Time in the midi note occurs
┃ ┗ note                : String                        // String note from the NOTE_NAMES
┣ NoteEvent
┃ ┣ tick                : long                          // Game tick when event occurs
┃ ┣ location            : Schematic.Location            // Location the event occurs
┃ ┣ file                : File                          // The generated file for the dataPack
┃ ┣ sound               : SoundAtlas                    // Associated sound
┃ ┣ makeFunctionFile()  : File                          // Function that takes in a path and creates a file
┃ ┃                                                     // Write to the file the contents generated from the child class
┃ ┗ getFileContents()   : String                        // Abstract function for child to provide generated file contents
┣ PlaceBlockEvent
┃ ┣ block               : Schematic.Block               // Block being placed
┃ ┗ getFileContents()   : String                        // Generates the setblock command based on block
┣ Schematic         
┃ ┣ file                : File                          // NBT File schematic
┃ ┣ blocks              : ArrayList<Block>              // Array of all blocks in schematic
┃ ┃                                                     !! Add entity list for entities from schematic
┃ ┣ paletteIds          : HashMap<Integer, String[]>    // Parsed hashmap of id to [block name, block props]
┃ ┃                                                     ?? Could be arraylist because id is just incrementing
┃ ┣ size                : Location                      // Schematic Size
┃ ┣ Schematic()         : Schematic                     // Constructor
┃ ┃                                                     // Reading the file with NBTUtil
┃ ┃                                                     // Get and set the size
┃ ┃                                                     // Get the palette
┃ ┃                                                     // Loop over palette, getting Id, block name, and parsing properties
┃ ┃                                                     // Loop over blocks
┃ ┃                                                     // Get block from palette id and create new BLock object
┃ ┣ getBlock()  : Block                                 // Gets the next block in the snake of that sound
┃ ┣ Block
┃ ┃ ┣ name              : String                        // block id without minecraft:
┃ ┃ ┣ location          : Location                      // location of block in schematic 
┃ ┃ ┗ getFormatted()    : String                        // Gets the block + properties for the setblock command
┃ ┗ Location
┃   ┣ xOffset           : int                           // Hardcoded offset for all values
┃   ┣ yOffset           : int                           // Hardcoded offset for all values
┃   ┣ zOffset           : int                           // Hardcoded offset for all values
┃   ┣ x                 : int                           // x location
┃   ┣ y                 : int                           // y location
┃   ┗ z                 : int                           // z location
┗ SoundAtlas
  ┣ blocks              : ArrayList<String>             // Blocks with the sound
  ┣ textureID           : int                           // Texture in the gui
  ┣ soundSource         : SoundSource                   // Source of the sound
  ┗ Sound Source
    ┣ BLOCK                                             // Sounds that come from blocks
    ┗ ENTITY                                            // Sounds that come from entities

```