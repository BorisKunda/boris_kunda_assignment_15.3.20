package com.assignment.boris_kunda_assignment_15320.network;

import org.json.JSONArray;

public interface OnServerResponseListener {

    void OnServerResponse (boolean isSuccess, JSONArray response);

}

