package org.freerealm.settlement.improvement;

import java.util.Iterator;
import org.freerealm.property.ProduceResource;
import org.freerealm.property.Property;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.FreeRealmSettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmSettlementImprovementType extends FreeRealmSettlementBuildable implements SettlementImprovementType {

    private int maximumWorkers;

    public int getInputResourceCount() {
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                return produceResource.getInputCount();
            }
        }
        return 0;
    }

    public int getInputQuantity(Resource resource) {
        int inputQuantity = 0;
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                inputQuantity = inputQuantity + produceResource.getInputQuantity(resource);
            }
        }
        return inputQuantity;
    }

    public int getOutputResourceCount() {
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                return produceResource.getOutputCount();
            }
        }
        return 0;
    }

    public int getOutputQuantity(Resource resource) {
        int outputQuantity = 0;
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                outputQuantity = outputQuantity + produceResource.getOutputQuantity(resource);
            }
        }
        return outputQuantity;
    }

    public int getMaximumMultiplier() {
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                return produceResource.getMaximumMultiplier();
            }
        }
        return 0;
    }

    public Iterator<Resource> getInputResourcesIterator() {
        Iterator<Resource> inputResourcesIterator = null;
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                inputResourcesIterator = produceResource.getInputResourcesIterator();
            }
        }
        return inputResourcesIterator;
    }

    public Iterator<Resource> getOutputResourcesIterator() {
        Iterator<Resource> outputResourcesIterator = null;
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                ProduceResource produceResource = (ProduceResource) property;
                outputResourcesIterator = produceResource.getOutputResourcesIterator();
            }
        }
        return outputResourcesIterator;
    }

    public boolean isResourceProducer() {
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ProduceResource) {
                return true;
            }
        }
        return false;
    }

    public int getMaximumWorkers() {
        return maximumWorkers;
    }

    public void setMaximumWorkers(int maximumWorkers) {
        this.maximumWorkers = maximumWorkers;
    }

}
