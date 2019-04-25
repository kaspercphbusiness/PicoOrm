package cphkasper.pico.model;

/**
 * The purpose of PicoField is to...
 * @author kasper
 */
class PicoField {
    public PicoEntity myEntity;
    public String name;
    public String typeSpec;

    PicoField( PicoEntity entity, String name, String typeSpec ) {
        this.myEntity = entity;
        this.name = name;
        this.typeSpec = typeSpec;
    }
    
    public boolean isList(){
        return typeSpec.startsWith( "*");
    }
    
    public String getListType(){
        return typeSpec.substring(1,typeSpec.length());
    }
    
    public boolean isForeignKey(){
        return !(  "String".equals( typeSpec) 
                || "Number".equals( typeSpec)
                || "*".equals( typeSpec.substring(0,1)));
    }
    
    public String javaCode(){
        String template = " private $type $name;\n"
                + " public $type get$Name(){return $name;}\n"
                + " public void set$Name($type newVal){$name = newVal;}\n\n";
        // Raise first letter of field to capital letter
        String capField = name.substring(0, 1).toUpperCase() + name.substring(1);
        // Java do not have embedded values in strings, so this is a workaround
        return template
                .replace( "$type", javaType())
                .replace( "$name", name)
                .replace( "$Name", capField);
    }
    
    public String sqlCode(){
        String template = "$name $type,\n";
        // Java do not have embedded values in strings, so this is a workaround
        return template
                .replace( "$type", sqlType())
                .replace( "$name", name);
    }
    
    private String javaType(){
        if ("String".equals( typeSpec) ) return "String";
        if ("Number".equals( typeSpec) ) return "Integer";
        if ("*".equals( typeSpec.substring(0,1) ))
            return "List<"+typeSpec.substring(1)+">";
        else
            return typeSpec;
    }
    
    private String sqlType(){
        if ("String".equals( typeSpec) ) return "VARCHAR(100)";
        if ("Number".equals( typeSpec) ) return "INT";
        if ("*".equals( typeSpec.substring(0,1) ))
            throw new RuntimeException("Should not make fields for lists in SQL");
        else
            return "INT";
    }
}
