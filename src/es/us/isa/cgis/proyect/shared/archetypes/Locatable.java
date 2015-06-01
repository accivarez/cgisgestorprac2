package es.us.isa.cgis.proyect.shared.archetypes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Locatable implements Cloneable {
    public Locatable(String ID, String archetypeNodeId, String name,
                        String archetypeDetails) {
        if (archetypeNodeId == null) {
            throw new IllegalArgumentException("null archetypeNodeId");
        }
        if (name == null) {
            throw new IllegalArgumentException("null name");
        }
        this.ID = ID;
        this.archetypeNodeId = archetypeNodeId;
        this.name = name;
        this.archetypeDetails = archetypeDetails;

    }

    
    protected Locatable(String archetypeNodeId, String name) {
        this(null, archetypeNodeId, name, null);
    }

    
    public String getUid() {
        return ID;
    }

    
    public String getArchetypeNodeId() {
        return archetypeNodeId;
    }

    public String getOriginalArchetypeNodeId() {
        return originalArchetypeNodeId;
    }

    public String setOriginalArchetypeNodeId(String originalArchetypeNodeId) {
        return this.originalArchetypeNodeId = originalArchetypeNodeId;
    }

    
    public String getName() {
        return name;
    }

    
    public String getArchetypeDetails() {
        return archetypeDetails;
    }
    
    public boolean isArchetypeRoot() {
        return archetypeDetails != null;
    }

    
    //public abstract String pathOfItem(Pathable item);

    
    public static List<String> dividePathIntoSegments(String path) {
    	List<String> segments = new ArrayList<String>();
    	StringTokenizer tokens = new StringTokenizer(path, "/");
    	while(tokens.hasMoreTokens()) {
    		String next = tokens.nextToken();
    		if (next.matches(".+\\[.+[^\\]]$")) {
    			do {
    				next = next + "/" + tokens.nextToken();
    			} while (!next.matches(".*]$"));
    		}
    		segments.add(next);
    	}
    	return segments;
    }

    
    private Object genericPathEvaluator(String path, Object object) {
    	if(path == null || object == null) {
    		return null;
    	}
    	List<String> segments = dividePathIntoSegments(path);
    	return evaluatePathSegment(segments, object);
    }

    
    private Object evaluatePathSegment(List<String> pathSegments,
    		Object object) {
    	if(pathSegments.isEmpty()) {
    		return object;
    	}
    	String pathSegment = pathSegments.remove(0);
    	Object value =  null;

    	int index = pathSegment.indexOf("[");
    	String expression = null;
    	String attributeName = null;

    	// has [....] predicate expression
    	if(index > 0) {

    		assert(pathSegment.indexOf("]") > index);

    		attributeName = pathSegment.substring(0, index);
    		expression = pathSegment.substring(index + 1,
    				pathSegment.indexOf("]"));
    	} else {
    		attributeName = pathSegment;
    	}

    	value = getAttributeValue(object, attributeName);
    	if(expression != null && value != null ) {
    		value = processPredicate(expression, value);
    	}
        if(expression == null && value instanceof ArrayList && !pathSegments.isEmpty()) {
			ArrayList<?> arrayList = (ArrayList<?>)Arrays.asList(value);
            if (!arrayList.isEmpty()){
                value = arrayList.get(0);
            }
        }
    	if(value != null) {
    		return evaluatePathSegment(pathSegments, value);
    	}
    	return null;
    }

    public String toCamelCase(String underscoreSeparated) {
    	if( ! underscoreSeparated.contains("_")) {
    		return underscoreSeparated;
    	}
		StringTokenizer tokens = new StringTokenizer(underscoreSeparated, "_");
		StringBuffer buf = new StringBuffer();
		while (tokens.hasMoreTokens()) {
			String word = tokens.nextToken();
			if (buf.length() == 0) {
				buf.append(word);
			} else {
				buf.append(word.substring(0, 1).toUpperCase());
				buf.append(word.substring(1));
			}
		}
		return buf.toString();
	}

    public String toFirstUpperCaseCamelCase(String name) {
    	name = toCamelCase(name);
    	return name.substring(0, 1).toUpperCase()
		+ name.substring(1);
    }

    
    @SuppressWarnings("unchecked")
	Object processPredicate(String expression, Object object) {
    	String name = null;
    	String archetypeNodeId = null;
    	expression = expression.trim();
    	int index;

    	// shortcut syntax, [at0001, 'standing']
    	if(expression.contains(",")
    			// avoid [at0001 and/value='status, 2nd']
    			&& expression.indexOf(",") < expression.indexOf("'")) {
    		index = expression.indexOf(",");
    		archetypeNodeId = expression.substring(0, index).trim();
    		name = expression.substring(expression.indexOf("'") + 1,
    				expression.lastIndexOf("'"));

    	// [at0006 and name/value='any event']
    	// [at0006 AND name/value='any event']
    	} else if(expression.contains(" AND ")
    			|| expression.contains(" and ")) {

    		// OG - 20100401: Fixed bug where the name contained 'AND' or 'and',
    		// i.e. 'MEDICINSK BEHANDLING'.
    		if(expression.contains(" AND ")) {
    			index = expression.indexOf(" AND ");
    		} else {
    			index = expression.indexOf(" and ");
    		}
    		archetypeNodeId = expression.substring(0, index).trim();
    		name = expression.substring(expression.indexOf("'") + 1,
    				expression.lastIndexOf("'"));
    	// just name, ['standing']
    	} else if (expression.startsWith("'") && expression.endsWith("'")) {
    		name = expression.substring(1, expression.length() - 1);

    	// archetyped root node id or at-coded node
    	// [at0006] or [openEHR-EHR-OBSERVATION.laboratory-lipids.v1]
    	} else {
    		archetypeNodeId = expression;
    	}

    	Iterable<Object> collection = null;
    	if(object instanceof Iterable) {
    		collection = (Iterable<Object>) object;
    	} else {
    		List<Object> list = new ArrayList<Object>();
    		list.add(object);
    		collection = list;
    	}

    	for(Object item : collection) {
    		if(item instanceof Locatable) {
    			Locatable locatable = (Locatable) item;
        		if(archetypeNodeId != null
        				&& !locatable.archetypeNodeId.equals(archetypeNodeId)) {
        			continue;
        		}
        		if(name != null && !locatable.name.equals(name)) {
        			continue;
        		}
    		}
    		// TODO other non-locatable predicates!!
    		// e.g. time > 10:20:15
    		return item; // found a match!
    	}
    	return null;
    }

    
    public Object itemAtPath(String path) {
    	if (path == null) {
            throw new IllegalArgumentException("invalid path: " + path);
        }
        if (Locatable.ROOT.equals(path) || path.equals(whole())) {
            return this;
        }
        return genericPathEvaluator(path, this);
    }

    
    private Object getAttributeValue(Object obj, String attribute) {
    	Class<? extends Object> rmClass = obj.getClass();
    	Object value = null;
    	Method getter = null;
    	String getterName = "get" + toFirstUpperCaseCamelCase(attribute);

    	try {
    		getter = rmClass.getMethod(getterName, (Class<?>)null);
			//getter = rmClass.getMethod(getterName, null);
			//value = getter.invoke(obj, null);
			value = getter.invoke(obj, (Object)null);

    	} catch(Exception e) {
			// TODO log as kernel warning
			// e.printStackTrace();
		}
		return value;
    }

    
    private void setAttributeValue(Object obj, String attribute, Object value) {
    	Class<? extends Object> rmClass = obj.getClass();
    	String setterName = "set" + toFirstUpperCaseCamelCase(attribute);

    	try {
			Method setter = getMethodByName(rmClass, setterName);
			if(setter == null) {
				throw new IllegalArgumentException("unkown setter method: " + setterName + " for rmClass=" + rmClass);
			}
			setter.invoke(obj, value);

    	} catch(Exception e) {
    		// TODO log as kernel warning
			e.printStackTrace();
		}
    }

    private Method getMethodByName(Class<? extends Object> klass, String method) {
    	Method[] methods = klass.getMethods();
    	for(Method m : methods) {
    		if(method.equals(m.getName())) {
    			return m;
    		}
    	}
    	return null;
    }


    public void set(String path, Object value) {
    	int i = path.lastIndexOf("/");
    	if(i < 0 || i == path.length()) {
    		throw new IllegalArgumentException(
    				"invalid path for setting value: " + path);
    	}
    	String objPath = "/";
    	if(i > 0) {
    		objPath = path.substring(0, i);
    	}
    	String attributeName = path.substring(i + 1);

    	Object obj = itemAtPath(objPath);
    	if(obj == null) {
    		throw new IllegalArgumentException("Item not found on path: " + path);
    	}
    	setAttributeValue(obj, attributeName, value);
    }

    
    public static String parentPath(String path) {
    	List<String> list = dividePathIntoSegments(path);
    	int pathLevel = list.size();
    	if(pathLevel == 0) {
    		throw new IllegalArgumentException("Unable to compute parent path: "
    				+ path);
    	} else if(pathLevel == 1) {
    		return PATH_SEPARATOR;
    	}
    	StringBuffer buf = new StringBuffer();
    	for(int j = 0; j < pathLevel - 1; j++) {
    		buf.append(PATH_SEPARATOR);
    		buf.append(list.get(j));
    	}
    	return buf.toString();
    }

    public void addChild(String path, Object child) {
    	List<String> list = dividePathIntoSegments(path);
    	int pathLevel = list.size();
    	String objPath = PATH_SEPARATOR;

    	if(pathLevel > 1) {
    		StringBuffer buf = new StringBuffer();
    		for(int j = 0; j < pathLevel - 1; j++) {
    			buf.append(PATH_SEPARATOR);
    			buf.append(list.get(j));
    		}
    		objPath = buf.toString();
    	}

    	Object obj = itemAtPath(objPath);
    	if(obj == null) {
    		throw new IllegalArgumentException("Item not found on path: " + path);
    	}

    	String attributeName = list.get(list.size() - 1);
    	Object attributeValue = getAttributeValue(obj, attributeName);
    	if(attributeValue == null) {
    		attributeValue = new ArrayList<Object>();
    	}
    	if(attributeValue instanceof List) {
    		ArrayList<Object> parent = (ArrayList<Object>) Arrays.asList(attributeValue);
    		parent.add(child);
    	} else {
    		throw new IllegalArgumentException(
    				"non-container parent attribute on path: " + path);
    	}
    }

    public void removeChild(String path) {
    	int i = path.lastIndexOf(PATH_SEPARATOR);
    	if(i < 0 || i == path.length()) {
    		throw new IllegalArgumentException(
    				"invalid path for setting value: " + path);
    	}
    	String objPath = PATH_SEPARATOR;
    	List<String> list = dividePathIntoSegments(path);
    	int pathLevel = list.size();
    	if(pathLevel > 1) {
    		StringBuffer buf = new StringBuffer();
    		for(int j = 0; j < pathLevel - 1; j++) {
    			buf.append(PATH_SEPARATOR);
    			buf.append(list.get(j));
    		}
    		objPath = buf.toString();
    	}

    	Object obj = itemAtPath(objPath);
    	if(obj == null) {
    		throw new IllegalArgumentException("Item not found on path: " + path);
    	}

    	Object child = itemAtPath(path);
    	if(child == null) {
    		throw new IllegalArgumentException("Unknown child on path: " + path);
    	}

    	String attributeName = list.get(list.size() - 1);
    	int predicateIndex = attributeName.indexOf("[");
    	if(predicateIndex > 0) {
    		attributeName = attributeName.substring(0, predicateIndex);
    	}
    	Object attributeValue = getAttributeValue(obj, attributeName);

    	if(attributeValue == null) {
    		throw new IllegalArgumentException(
    				"parent attribute not found on path: " + path);
    	}
    	if(attributeValue instanceof List) {

    		List<?> parent = (List<?>) attributeValue;
    		parent.remove(child);

    	} else {
    		throw new IllegalArgumentException(
    				"non-container parent attribute on path: " + path);
    	}
	}

	


    
    @Override
	public String toString() {
        return archetypeNodeId.equals(name) ?
                archetypeNodeId.toString() : archetypeNodeId + ", " + name;
    }

    



    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime
				* result
				+ ((archetypeDetails == null) ? 0 : archetypeDetails.hashCode());
		result = prime * result
				+ ((archetypeNodeId == null) ? 0 : archetypeNodeId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((originalArchetypeNodeId == null) ? 0
						: originalArchetypeNodeId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locatable other = (Locatable) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (archetypeDetails == null) {
			if (other.archetypeDetails != null)
				return false;
		} else if (!archetypeDetails.equals(other.archetypeDetails))
			return false;
		if (archetypeNodeId == null) {
			if (other.archetypeNodeId != null)
				return false;
		} else if (!archetypeNodeId.equals(other.archetypeNodeId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (originalArchetypeNodeId == null) {
			if (other.originalArchetypeNodeId != null)
				return false;
		} else if (!originalArchetypeNodeId
				.equals(other.originalArchetypeNodeId))
			return false;
		return true;
	}


	@Override public Locatable clone() {
    	try {
    		return (Locatable) super.clone();
    	} catch(CloneNotSupportedException e) {
    		throw new AssertionError();
    	}
    }

    
    public String whole() {
        return ROOT;// + "[" + getName().getValue() + "]";
    }

    public String nodeName() {
        return "[" + getName() + "]";
    }

    public String atNode() {
    		return ROOT + "[" + getArchetypeNodeId() + "]";
    }

    // POJO start
    protected Locatable() {
    }

    public void setUid(String uid) {
    	this.ID = uid;
    }

    protected void setArchetypeNodeId(String archetypeNodeId) {
        this.archetypeNodeId = archetypeNodeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void setArchetypeDetails(String archetypeDetails) {
        this.archetypeDetails = archetypeDetails;
    }
    // POJO end

    
    public static final String PATH_SEPARATOR = "/";
    public static final String ROOT = PATH_SEPARATOR;

    
    private String ID;
    private String archetypeNodeId;
    private String originalArchetypeNodeId;
    private String name;
    private String archetypeDetails;

}