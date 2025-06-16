package model;

import java.util.HashMap;
import java.util.Map;

public class ClassValidatorFactory {

    private static ClassValidatorFactory instance;
    private Map<String, ClassValidator> classValidationMap;

    private ClassValidatorFactory(){
        classValidationMap = new HashMap<>();
        classValidationMap.put(ClassTimeValidator.class.getName(), new ClassTimeValidator());
    }

    public static ClassValidatorFactory getInstance() {
        if(instance == null){
            instance = new ClassValidatorFactory();
        }
        return instance;
    }

    public ClassValidator getClassValidator(String validatorName){
        if(classValidationMap.containsKey(validatorName)){
            return classValidationMap.get(validatorName);
        }
        throw new IllegalArgumentException();
    }
}
