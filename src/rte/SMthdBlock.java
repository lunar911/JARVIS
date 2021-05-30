package rte;

public class SMthdBlock {
    public String namePar; //einfacher Name, vollqualifizierte Parametertypen
    public SMthdBlock nextMthd; //nächste Methode der aktuellen Klasse
    public int modifier; //Modifier der Methode
    public int[] lineInCodeOffset; //optionale Zeilen-Zuordnung zu Code-Bytes**
    public SClassDesc owner;
}