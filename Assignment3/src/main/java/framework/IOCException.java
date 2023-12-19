package framework;

/**
 * 本类封装在实现IOC容器过程中可能会出现的异常
 */

public class IOCException extends Exception{

    public enum ErrorType{XML_READ_ERROR,BEAN_NOT_FOUND,ID_NOT_FOUND,CLAZZ_NOT_FOUND,CLASS_NOT_FOUND,CREATE_OBJECT_ERROR,Method_NOT_FOUND,METHOD_INVOKE_ERROR}

    private ErrorType errorType;

    public IOCException(ErrorType errorType,String message)
    {
        super(message);
        this.errorType=errorType;
    }


    public ErrorType getErrorType() {
        return errorType;
    }
}
