package com.mxgraph.svg2xml;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class mxPolyParser2
{
    private int dtr;
    private boolean doRound = false;

    public Element createShape(List<double[]> pointList, Document parentDoc,
        int decimals, String tagName)
    {
        if (decimals >= 0)
        {
            this.doRound = true;
            this.dtr = decimals;
        }

        Element pathEl = parentDoc.createElement("path");
        for (int i = 0; i < pointList.size(); i++)
        {
            double[] pt = pointList.get(i);
            
            double x = pt[0];
            double y = pt[1];
            
            Element currChild = null;
            if (i == 0)
            {
                currChild = parentDoc.createElement("move");
            }
            else
            {
                currChild = parentDoc.createElement("line");
            }
            currChild.setAttribute("x", Double.toString(rtd(x)));
            currChild.setAttribute("y", Double.toString(rtd(y)));
            
            pathEl.appendChild(currChild);
        }

        if (tagName.toLowerCase().equals("polygon"))
        {
            pathEl.appendChild(parentDoc.createElement("close"));
        }

        return pathEl;
    }

    /**
     * @param d number to round
     * @param c decimals to round to
     * @return rounded <b>d</b> to <b>c</b> decimals
     */
    private double rtd(double d)
    {
        if (doRound)
        {
            BigDecimal temp = new BigDecimal(Double.toString(d));
            temp = temp.setScale(dtr, RoundingMode.HALF_EVEN);
            return temp.doubleValue();
        }
        else
        {
            return d;
        }
    }
}
