module me.melonboy10.midi2building {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    opens me.melonboy10.midi2building to javafx.fxml;
    exports me.melonboy10.midi2building;
    exports me.melonboy10.midi2building.screenElements;
    opens me.melonboy10.midi2building.screenElements to javafx.fxml;
    exports me.melonboy10.midi2building.conversion;
    opens me.melonboy10.midi2building.conversion to javafx.fxml;
}