/*
 * Copyright (c) 2015 John May <jwmay@users.sf.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version. All we ask is that proper credit is given
 * for our work, which includes - but is not limited to - adding the above
 * copyright notice to the beginning of your source code files, and to any
 * copyright notice that you may distribute with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 U
 */

package org.openscience.cdk.depict;

import org.junit.Test;
import org.openscience.cdk.renderer.elements.GeneralPath;
import org.openscience.cdk.renderer.elements.LineElement;
import org.openscience.cdk.renderer.elements.MarkedElement;
import org.openscience.cdk.renderer.elements.RectangleElement;
import org.openscience.cdk.renderer.elements.TextElement;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SvgDrawVisitorTest {

    @Test
    public void empty() {
        String empty = new SvgDrawVisitor(50, 50).toString();
        assertThat(empty, is("<?xml version='1.0' encoding='UTF-8'?>\n"
                             + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                             + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                             + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                             + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                             + "  </g>\n"
                             + "</svg>"));
    }

    @Test
    public void markedElement() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(50, 50);
        visitor.visit(MarkedElement.markup(new LineElement(0, 0, 1, 1, 0.5, Color.RED),
                                           "test-class"));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <g class='test-class'>\n"
                                          + "      <line  x1='0' y1='0' x2='1' y2='1' stroke='#FF0000' stroke-width='0.5'/>\n"
                                          + "    </g>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void translatedLine() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(50, 50);
        visitor.visit(new LineElement(0, 0, 1, 1, 0.5, Color.RED));
        visitor.setTransform(AffineTransform.getTranslateInstance(10, 10));
        visitor.visit(new LineElement(0, 0, 1, 1, 0.5, Color.RED));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <line  x1='0' y1='0' x2='1' y2='1' stroke='#FF0000' stroke-width='0.5'/>\n"
                                          + "    <line  x1='10' y1='10' x2='11' y2='11' stroke='#FF0000' stroke-width='0.5'/>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void scaledStroke() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(50, 50);
        visitor.visit(new LineElement(0, 0, 1, 1, 0.5, Color.RED));
        visitor.setTransform(AffineTransform.getScaleInstance(2, 2));
        visitor.visit(new LineElement(0, 0, 1, 1, 0.5, Color.RED));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <line  x1='0' y1='0' x2='1' y2='1' stroke='#FF0000' stroke-width='0.5'/>\n"
                                          + "    <line  x1='0' y1='0' x2='2' y2='2' stroke='#FF0000' stroke-width='1'/>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void filledPath() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(50, 50);
        visitor.visit(GeneralPath.shapeOf(new RoundRectangle2D.Double(0,0,10,10,2,2), Color.BLUE));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <path d='M 0 1 L 0 9 C 0 9.55 0.45 10 1 10 L 9 10 C 9.55 10 10 9.55 10 9 L 10 1 C 10 0.45 9.55 0 9 0 L 1 0 C 0.45 0 0 0.45 0 1 Z ' stroke='none' fill='#0000FF'/>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void transformedPath() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(50, 50);
        visitor.setTransform(AffineTransform.getTranslateInstance(15, 15));
        visitor.visit(GeneralPath.shapeOf(new RoundRectangle2D.Double(0, 0, 10, 10, 2, 2), Color.BLUE));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='50' height='50'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <path d='M 15 16 L 15 24 C 15 24.55 15.45 25 16 25 L 24 25 C 24.55 25 25 24.55 25 24 L 25 16 C 25 15.45 24.55 15 24 15 L 16 15 C 15.45 15 15 15.45 15 16 Z ' stroke='none' fill='#0000FF'/>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void textElements() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(100, 100);
        visitor.visit(new TextElement(50, 50, "PNG < EPS < SVG", Color.RED));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='100' height='100'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <text  x='50' y='50' fill='#FF0000' text-anchor='middle'>PNG &lt; EPS &lt; SVG</text>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

    @Test
    public void rectElements() {
        final SvgDrawVisitor visitor = new SvgDrawVisitor(100, 100);
        visitor.visit(new RectangleElement(0,0,100,100, Color.WHITE));
        assertThat(visitor.toString(), is("<?xml version='1.0' encoding='UTF-8'?>\n"
                                          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
                                          + "<svg version='1.2' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' width='100' height='100'>\n"
                                          + "  <desc>Generated with the Chemistry Development Kit (http://github.com/cdk)</desc>\n"
                                          + "  <g stroke-linecap='round' stroke-linejoin='round'>\n"
                                          + "    <rect x='0' y='0' width='100' height='100' fill='none' stroke='#FFFFFF'/>\n"
                                          + "  </g>\n"
                                          + "</svg>"));
    }

}