package cphkasper.pico.orm;

import cphkasper.pico.model.PicoPackage;
import org.json.*;

/**
 The purpose of PicoOrmGenerator is to generate java class definitions from
 json.
 This is just a sketch of such a thing, it should read the json string
 from a file.

 @author kasper
 */
public class PicoOrmGenerator {

    // We should read the json string from a file
    public static final String JSONSCHEMA
            = "{'schemaName': 'MicroShop',\n"
            + "  'entities': [\n"
            + "  	{'Customer': {\n"
            + "  		'name': 'String',\n"
            + "  		'orders' :'*Order'}},\n"
            + "  	{'Order' :{\n"
            + "  		'date': 'String',\n"
            + "  		'total': 'Number',\n"
            + "  		'customer': 'Customer',\n"
            + "  		'lines': '*OrderLine' }},\n"
            + "  	{'OrderLine' : {\n"
            + "  		'order': 'Order',\n"
            + "  		'product': 'Product',\n"
            + "  		'count': 'Number',\n"
            + "  		'total': 'Number' }},\n"
            + "  	{'Product' : {\n"
            + "  		'name': 'String',\n"
            + "  		'price' :'Number'}}\n"
            + "  ]\n"
            + "}".replace( '\'', '"' ); // Java trick to solve a quotemark hell

    public static PicoPackage getPackage() throws JSONException {
        JSONObject schema = new JSONObject( JSONSCHEMA );
        return new PicoPackage( schema );
    }

    public static void main( String[] args ) {
        PicoPackage pp = getPackage();
        //pp.javaCode();
        System.out.println( pp.sqlCode() );
    }

}
