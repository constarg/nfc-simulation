package com.exercise.networking_device_programming;

import android.nfc.NdefRecord;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class NFCSimulation {
    private static ArrayList<NdefRecord> records = new ArrayList<>();

    // initialize virtual tags.
    private static void init_v_tags() {
        if (records.isEmpty()) {
            // Add a few vtags.
            NFCSimulation.records.add(NdefRecord.createTextRecord(
                    String.valueOf(Locale.ENGLISH),
                    "The Hobbit"));

            NFCSimulation.records.add(NdefRecord.createTextRecord(
                    String.valueOf(Locale.ENGLISH),
                    "The Fellowship of the Ring"));

            NFCSimulation.records.add(NdefRecord.createTextRecord(
                    String.valueOf(Locale.ENGLISH),
                    "The Two Towers"));

            NFCSimulation.records.add(NdefRecord.createTextRecord(
                    String.valueOf(Locale.ENGLISH),
                    "The Return of the King"));

            // Simulate the scenario that the tag not detected.
            NFCSimulation.records.add(null);
            NFCSimulation.records.add(null);
        }
    }

    public static NdefRecord read_tag() {
        // Initialize virtual tags.
        NFCSimulation.init_v_tags();

        // Select a random vtag.
        Random random = new Random();
        int vtag = random.nextInt(records.size() - 1);

        return records.get(vtag);
    }

    public static String extract_tag_value(NdefRecord tag) throws UnsupportedEncodingException {
        byte[] payload = tag.getPayload();
        String encoding = ((payload[0] & 0200) == 0)? "UTF-8" : "UTF-16";
        int langCodeLen = payload[0] & 0077;

        return new String(
                payload,
                langCodeLen + 1,
                payload.length - langCodeLen - 1,
                encoding);
    }
}
