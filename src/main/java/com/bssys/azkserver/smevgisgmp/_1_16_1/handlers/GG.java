package com.bssys.azkserver.smevgisgmp._1_16_1.handlers;

import com.bftcom.common.util.XML;
import com.bssys.util.base64.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Reads XML SOAP response, parses it, saves payments to files, adding suffix (suffix = SupplierBillId)
 * @author ioikka
 */
public class GG {

  public static void main(String[] args) {
    try {
      String inputFileName = "D:\\tmp\\2016_06_16_17_44_39_959_response.xml";
      String s = new String(Files.readAllBytes(Paths.get(inputFileName)), StandardCharsets.UTF_8);
      Document doc = XML.createDocument(s);
      NodeList paymentData = doc.getElementsByTagNameNS("*", "PaymentData");
      for (int i = 0; i < paymentData.getLength(); i++) {
        Node item = paymentData.item(i);
        
        byte[] decode = Base64.decode(item.getTextContent());
        String s1 = new String(decode, StandardCharsets.UTF_8);
        Document document = XML.createDocument(s1);
        NodeList supplierBillID = document.getElementsByTagNameNS("*", "SupplierBillID");
        Node item1 = supplierBillID.item(0);
        String textContent1 = item1.getTextContent();
        List<String> strings = Collections.singletonList(s1);
        Files.write(Paths.get("D:/" + i + "_" + textContent1 + " .xml"), strings);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
