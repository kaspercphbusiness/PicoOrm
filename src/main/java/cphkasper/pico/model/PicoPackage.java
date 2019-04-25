package cphkasper.pico.model;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

/**
 The purpose of PicoPackage is to...

 @author kasper
 */
public class PicoPackage {

    private static final String outDirectory = "/Users/kasper/NetBeansProjects/PicoORM/src/main/java/MicroShop/entities/";
    public String name;
    public Map<String, PicoEntity> entities;

    public PicoPackage( JSONObject schema ) {
        entities = new HashMap<>();
        name = schema.getString( "schemaName" );
        JSONArray allObjects = schema.getJSONArray( "entities" );
        for ( Object obj : allObjects ) {
            JSONObject jsonEntity = (JSONObject) obj;
            String className = jsonEntity.keys().next();
            PicoEntity entity = new PicoEntity( this, className, jsonEntity.getJSONObject( className ) );
            entities.put( className, entity );
        }
    }

    public void javaCode() {
        for ( PicoEntity entity : entities.values() ) {
            try ( PrintWriter writer = new PrintWriter( outDirectory + entity.name + ".java", "UTF-8" ) ) {
                writer.println( entity.javaCode() );
            } catch ( Exception ex ) {
                // Should handle it - but not focus in this exercise
            }
        }
    }

    public String sqlCode() {
        StringBuffer sql = new StringBuffer( "DROP DATABASE IF EXISTS " + name + "; \n" );
        sql.append( "CREATE SCHEMA " + name + ";\nUSE " + name + ";\n" );
        for ( PicoEntity entity : entities.values() ) {
            sql.append( entity.sqlCode() );
            sql.append( "\n\n" );
        }
        return sql.toString();
    }

    public String makeJoinCondition( String a, String joinClause, String b ) {
        PicoEntity aEntity = entities.get( a );
        PicoEntity bEntity = entities.get( b );
        if (aEntity.lists(b) || bEntity.refers(a)) { 
            // A 1--* B - B has foreign key to A, or
            String fromID = a + "ID";
            joinClause += b + "." + fromID + " = " + a + "." + fromID + " AND ";
        } else {
            // A *-1 B - A has foreign key to B
            String fromID = b + "ID";
            joinClause += b + "." + fromID + " = " + a + "." + fromID + " AND ";
        }
        return joinClause;
    }
}
