
export class ObjectUtils{
    public static isPrimitive(test:any) {
        return typeof test != 'object'
    }

    public static isStringArray(object : any): object is string[]{
        if(!Array.isArray(object)){
            return false;
        }
    
        for(let i = 0; i < object.length; i++){
            if((typeof object[i]) != 'string'){
                return false;
            }
        }
        return true
    }

    public static isString(object : any) : object is string {
        return (typeof object) == 'string' 
    }
}