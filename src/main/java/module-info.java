module me.melonboy10.midi2building {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens me.melonboy10.midi2building to javafx.fxml;
    exports me.melonboy10.midi2building;
}