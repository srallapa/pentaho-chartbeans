/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 2/7/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import org.jfree.resourceloader.ResourceException;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.css.resolver.impl.DefaultStyleResolver;

import java.net.URL;

/**
 * API for generating charts
 *
 * @author David Kincade
 */
public class ChartFactory {
  private static ResourceManager resourceManager;

  /**
   * Creats a chart based on the chart definition
   * TODO: document / complete
   *
   * @param chartURL the URL of the chart definition
   * @throws ResourceException      indicates an error loading the chart resources
   * @throws InvalidChartDefinition indicates an error with chart definition
   */
  public static ChartDocument generateChart(URL chartURL) throws ResourceException {
    // Parse the chart
    ChartXMLParser chartParser = new ChartXMLParser();
    ChartDocument chart = chartParser.parseChartDocument(chartURL);

    // Create a ChartDocumentContext
    ChartDocumentContext cdc = new ChartDocumentContext(chart);

    // temporary
    return chart;
  }

  /**
   * Returns the initialized <code>StyleResolver</code>.
   * NOTE: this method is protected for testing purposes only
   */
  protected static StyleResolver getStyleResolver(final ChartDocumentContext cdc) {
    StyleResolver sr = new DefaultStyleResolver();
    sr.initialize(cdc);
    return sr;
  }

  /**
   * Resolves the style information for all the elements in the chart document
   *
   * @param chart the chart document to process
   * @param cdc   the chart document context used with the <code>StyleResolver</code>
   */
  protected static void resolveStyles(final ChartDocument chart, final ChartDocumentContext cdc) {
    // Get the style resolveer
    StyleResolver sr = getStyleResolver(cdc);

    // Resolve the style for all the nodes in the chart
    ChartElement element = chart.getRootElement();
    while (element != null) {
      // Resolve this element's style
      sr.resolveStyle(element);

      // Get the next element to process
      element = element.getNextDepthFirstItem();
    }
  }
}
