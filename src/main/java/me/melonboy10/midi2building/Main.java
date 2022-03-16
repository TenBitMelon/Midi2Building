package me.melonboy10.midi2building;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    File datapackOutput;
    File somg = new File("src/main/resources/defaultSongs/11.mid");
    Scanner reader;
    {
        reader = new Scanner(String.valueOf(this.getClass().getResource("src/main/resources/resourceLocations.txt")));
        datapackOutput = new File(reader.nextLine());
    }

    public static void main(String[] args) {

    }

}
