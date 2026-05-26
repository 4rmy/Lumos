package dev.army.lumos.modules.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class NumberSetting extends SettingValue<Double> {

    private final double min;
    private final double max;
    private final double increment;

    public NumberSetting(String name, double defaultValue, double min, double max, double increment) {
        super(name, defaultValue);

        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    @Override
    public void set(Double value) {

        value = Math.clamp(value, min, max);

        value = Math.round(value / increment) * increment;
        value = Math.round(value * 1000.0) / 1000.0;

        super.set(value);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getIncrement() {
        return increment;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(get());
    }

    @Override
    public void deserialize(JsonElement element) {
        set(element.getAsDouble());
    }
}