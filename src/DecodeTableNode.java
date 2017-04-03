
public class DecodeTableNode {
    private char value;
    private String code;


    public DecodeTableNode(char value, String code) {
        this.value = value;
        this.code = code;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
