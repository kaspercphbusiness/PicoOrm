package cphkasper.pico.model;

import java.util.HashMap;
import java.util.Map;
import org.json.*;

/**
 The purpose of PicoEntity is to...

 @author kasper
 */
class PicoEntity {

    public PicoPackage myPackage;
    public String name;
    public Map<String, PicoField> fields;

    PicoEntity( PicoPackage p, String className, JSONObject jsonAttributes ) {
        fields = new HashMap<>();
        myPackage = p;
        name = className;
        for ( Object obj : jsonAttributes.keySet() ) {
            String fieldname = (String) obj;
            PicoField pf = new PicoField( this, fieldname, jsonAttributes.getString( fieldname ) );
            fields.put( fieldname, pf );
        }
    }

    public String javaCode() {
        StringBuilder code = new StringBuilder( "package " + myPackage.name + ".entities;\n" );
        code.append( "import java.util.List;\n" );
        code.append( "public class " + name + " {\n" );
        for ( PicoField field : fields.values() ) {
            code.append( field.javaCode() );
        }
        code.append( "}\n" );
        return code.toString();
    }

    public String sqlCode() {
        StringBuilder code = new StringBuilder( "CREATE TABLE " + name + " (\n" );
        code.append( "  " + name + "Id INT,\n" );
        StringBuilder foreignKeys = new StringBuilder();
        for ( PicoField field : fields.values() ) {
            if ( !field.isList() ) {
                code.append( "  " + field.sqlCode() );
            }
            if ( field.isForeignKey() ) //FOREIGN KEY (myFKId) REFERENCES Other(primaryID)
            {
                foreignKeys.append( ",\n  FOREIGN KEY (" + field.name + "ID)"
                        + " REFERENCES " + field.typeSpec + "( " + field.name + "ID )" );
            }
        }
        code.append( "  PRIMARY KEY( " + name + "Id )" );
        code.append( foreignKeys.toString() );
        code.append( "\n);\n" );
        return code.toString();
    }

    boolean refers( String entityName ) {
        for (PicoField pf : fields.values()){
            if (pf.typeSpec.equals( entityName)) return true;
        }
        return false;
    }

    boolean lists( String entityName ) {
        for (PicoField pf : fields.values()){
            if (pf.isList() &&  pf.getListType().equals( entityName)) return true;
        }
        return false;
    }
}
