package com.transfar.messageserver.vo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import com.transfar.messageserver.utils.MessageServerException;

public abstract class InteractionBasicObject implements InteractionObject {

	public InteractionBasicObject() {

	}

	public abstract String getRootName();

	public InteractionBasicObject(OMElement element)
			throws MessageServerException {

		if (!element.getLocalName().equalsIgnoreCase(getRootName()))
			throw new MessageServerException(
					MessageServerException.INTERACTION_ROOT_ELEMENT_DISMATCH);

		Iterator<OMElement> childs = (Iterator<OMElement>) element
				.getChildElements();

		if (childs != null) {
			while (childs.hasNext()) {
				OMElement child = childs.next();
				if (child.getChildElements() == null
						|| !child.getChildElements().hasNext()) {
					setAttribute(child.getLocalName(), child.getText());
				} else {
					setAttribute(child.getLocalName(), child);
				}
			}
		}
	}

	protected abstract void setAttribute(String name, String value)
			throws MessageServerException;

	protected abstract void setAttribute(String name, OMElement value)
			throws MessageServerException;

	protected abstract Map<String, Object> getAttributesMap();

	public OMElement getOMElement(OMNamespace omNs, OMFactory fac) {

		return getOMElement(getRootName(), omNs, fac, getAttributesMap());
	}

	public boolean implementedInterface(Class c, String szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i].getName().equals(szInterface)) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x].getName().equals(szInterface)) {
						return true;
					} else if (implementedInterface(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return implementedInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}

	public OMElement getOMElement(String name, OMNamespace omNs, OMFactory fac,
			Map<String, Object> map) {
		OMElement element = fac.createOMElement(name, omNs);
		for (String key : map.keySet()) {
			Object item = map.get(key);
			if ( item==null ) {
				OMElement attr = fac.createOMElement(key, omNs);
				element.addChild(attr);
			} else if (implementedInterface(item.getClass(), "java.util.List")) {
				List<Object> objList=(List)item;
				for ( Object listItem: objList) {
					if ( listItem==null) {
						OMElement attr = fac.createOMElement(key, omNs);
						element.addChild(attr);
					} else 	if (implementedInterface(listItem.getClass(),
							"com.transfar.messageserver.vo.InteractionObject"))  {
						InteractionObject interaction=(InteractionObject)listItem;
						element.addChild(interaction.getOMElement(omNs, fac));
					} else {
						OMElement attr = fac.createOMElement(key, omNs);
						attr.addChild(fac.createOMText(attr,listItem.toString()));
						element.addChild(attr);
					}
				}
			} else if (implementedInterface(item.getClass(),
					"com.transfar.messageserver.vo.InteractionObject")) {
				InteractionObject interaction=(InteractionObject)item;
				element.addChild(interaction.getOMElement(omNs, fac));
			} else {
				OMElement attr = fac.createOMElement(key, omNs);
				attr.addChild(fac.createOMText(attr,item.toString()));
				element.addChild(attr);
			}
		}
		return element;

	}

}
