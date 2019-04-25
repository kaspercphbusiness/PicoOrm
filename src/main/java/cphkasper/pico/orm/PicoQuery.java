package cphkasper.pico.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 The purpose of PicoQuery is to...

 @author kasper
 */
public class PicoQuery {

    public static void main( String[] args ) {

        translateQuery( "Customer" );
        translateQuery( "Customer.name" );
        translateQuery( "Customer.Order.OrderLine.price" );
        translateQuery( "(Customer|name='Joe').Order.OrderLine.price" );
        translateQuery( "(Customer|name='Joe').Order.OrderLine.Product" );
    }

    private static void translateQuery( String query ) {
        System.out.println( "Query: " + query );
        System.out.println( "SQL:\n" + createSQLQuery( query ) );
    }

    private static String createSQLQuery( String query ) {
        ArrayList<String> dotParts = new ArrayList<>( Arrays.asList( query.split( "\\." ) ) );
        String whereCondition = normalizeAndGetWhereCondition( dotParts );

        String selectPart = makeSelectPart( dotParts );

        String fromPart = makeFromPart( dotParts );
        //System.out.println( fromPart );
        String wherePart = makeWherePart( dotParts, whereCondition );
        //System.out.println( wherePart );
        return selectPart + "\n" + fromPart + "\n" + wherePart + "\n";
    }

    private static String normalizeAndGetWhereCondition( ArrayList<String> dotParts ) {
        String rootPart = dotParts.get( 0 );
        String whereCondition = "TRUE";
        if ( rootPart.startsWith( "(" ) ) {
            rootPart = rootPart.substring( 1, rootPart.length() - 1 );
            String[] rootParts = rootPart.split( "\\|" );
            dotParts.set( 0, rootParts[ 0 ] );
            whereCondition = rootParts[ 0 ] + "." + rootParts[ 1 ];
        }
        return whereCondition;
    }

    private static boolean lastIsColumn( List<String> dotParts ) {
        boolean lastIsColumn
                = Character.isLowerCase( dotParts.get( dotParts.size() - 1 ).charAt( 0 ) );
        return lastIsColumn;
    }

    private static String makeFromPart( ArrayList<String> dotParts ) {
        // Make the FROM part
        if ( lastIsColumn( dotParts ) ) { // ignore it for the rest
            dotParts.remove( dotParts.size() - 1 );
        }
        String fromPart = "FROM " + String.join( ", ", dotParts );
        return fromPart;
    }

    private static String makeWherePart( List<String> dotParts, String whereCondition ) {
        // Make the JOIN where clauses
        String joinClause = "";
        for ( int i = 0; i < dotParts.size() - 1; i++ ) {
            String a = dotParts.get( i );
            String b = dotParts.get( i + 1 );
            joinClause = makeJoinCondition( a, joinClause, b );
        }
        String wherePart = "WHERE " + joinClause + whereCondition;
        return wherePart;
    }

    private static String makeJoinCondition( String a, String joinClause, String b ) {
//        String aID = a + "ID";
//        joinClause += b + "." + aID + " = " + a + "." + aID + " AND ";
//        return joinClause;
          return PicoOrmGenerator.getPackage().makeJoinCondition( a, joinClause, b );
    }

    private static String makeSelectPart( List<String> dotParts ) {
        // Make the SELECT part
        String selectPart;
        if ( lastIsColumn( dotParts ) ) {
            selectPart = "SELECT " + dotParts.get( dotParts.size() - 1 - 1 ) + "." + dotParts.get( dotParts.size() - 1 );
        } else {
            selectPart = "SELECT " + dotParts.get( dotParts.size() - 1 ) + ".*";
        }
        //System.out.println( selectPart );
        return selectPart;
    }

}
