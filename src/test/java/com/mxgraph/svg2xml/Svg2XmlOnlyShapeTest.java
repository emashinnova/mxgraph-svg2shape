package com.mxgraph.svg2xml;


import static com.mxgraph.FileTooling.destinationFolder;
import static com.mxgraph.FileTooling.svgSourceFiles;
import static com.mxgraph.utils.FileUtils.fileContent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


class Svg2XmlOnlyShapeTest
{

    private static Svg2Xml newSvg2Xml()
    {
        return new Svg2Xml().infoLog(false);
    }

    @Test
    void convertToXml_single_file()
    {
        Svg2Xml svg2Xml = newSvg2Xml();

        File destPath = destinationFolder("Only shape");

        File[] src = svgSourceFiles("simple-01/circle-green.svg");
        String srcXmlString = Svg2Xml.readFile(src[0].getAbsolutePath());

        try
        {
            Document resDoc = svg2Xml.svg2shape(srcXmlString, "circle-green");

            // нет групповой ноды
            NodeList n = resDoc.getElementsByTagName("shapes");
            assertTrue((n == null || n.getLength() == 0));
            // только одна нода shape
            n = resDoc.getElementsByTagName("shape");
            assertTrue((n != null || n.getLength() == 1));

            // печать содержимого
            ByteArrayOutputStream groupBaos = new ByteArrayOutputStream();
            String resMxgraphXML = Svg2Xml.printDocumentString(resDoc,
                groupBaos);

            System.out.println(resMxgraphXML);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            fail();
        }
    }

}