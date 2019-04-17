/*
 * DSP4J - Java classes for dsp processing, https://github.com/aploese/dsp4j/
 * Copyright (C) ${project.inceptionYear}-2019, Arne Plöse and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package de.ibapl.dsp4j;

import java.io.File;
import java.io.IOException;
import de.ibapl.dsp4j.datatypes._short.ShortFileSink;
import org.junit.After;
import org.junit.Ignore;

/**
 *
 * @author aploese
 *
 * set setShowTests(true) in before... to show all tests
 */
@Ignore
public class VisualResultCheckTest {
    protected ShortFileSink sfs;
    private File f;
    
    @After
    public void tearDown() throws Exception {
        if (sfs != null) {
            sfs.close();
        }
        if (isShowResult()) {
            Runtime.getRuntime().exec(new String[]{"audacity", f.getAbsolutePath()}).waitFor();
        }
        sfs = null;
        if (f != null) {
            f.delete();
        }
        f = null;
    }

    /**
     * @return the showTests
     */
    protected boolean isShowResult() {
        return sfs != null;
    }

    /**
     * @param showTestName the showTests to set
     */
    protected void createFile(String name, double samplerate, int channels) throws IOException{
        this.f = File.createTempFile(String.format("%s_%s", getClass().getSimpleName(), name), ".wav");
        sfs = new ShortFileSink(f, channels, samplerate, 1024);
        f.deleteOnExit();
    }

    /**
     * @return the fs
     */
    protected File getFile() {
        return f;
    }

}
