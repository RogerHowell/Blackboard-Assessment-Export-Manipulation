package io.github.rogerhowell;

import org.everit.json.schema.Schema;
import org.json.JSONObject;

/**
 * This interface represents classes which can be represented in JSON.
 *
 * Classes which can be represented by JSON should also normally have as associated
 * schema describing what well-formed JSON looks like, and which values are valid.
 */
public interface Jsonable {

    Schema getSchema();

    JSONObject toJson();

}
