/**
 * <p>Copyright (c) The National Archives 2005-2010.  All rights reserved.
 * See Licence.txt for full licence details.
 * <p/>
 *
 * <p>DROID DCS Profile Tool
 * <p/>
 */
package uk.gov.nationalarchives.droid.command.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.gov.nationalarchives.droid.core.BinarySignatureIdentifier;

/**
 * @author rbrennan
 *
 */
public class NoProfileRunCommandTest {

    private NoProfileRunCommand command;
    private LocationResolver locationResolver;
//    private BinarySignatureIdentifier binarySignatureIdentifier;
    
    @Before
    public void setup() {
        locationResolver = mock(LocationResolver.class);
//        binarySignatureIdentifier = mock(BinarySignatureIdentifier.class);
        command = new NoProfileRunCommand();
        command.setLocationResolver(locationResolver);
    }
    @Ignore
    @Test
    public void testNoProfileRunWithNonexistentSignatureFile() {
        
        command.setSignatureFile("test");
        command.setResources(new String[] {
            "test1.txt",
            "test2.txt",
        });
        try {
            command.execute();
            fail("Expected CommandExecutionException");
        } catch (CommandExecutionException x) {
            assertEquals("Signature file not found", x.getMessage());
        }
    }
    @Ignore
    @Test
    public void testNoProfileRunWithNonexistentResource() throws Exception {
        
        File sigFile = new File("sigFile");
        sigFile.createNewFile();
        command.setSignatureFile("sigFile");
        command.setResources(new String[] {
            "test1.txt",
            "test2.txt",
        });
        try {
            command.execute();
            fail("Expected CommandExecutionException");
        } catch (CommandExecutionException x) {
            assertEquals("Resources directory not found", x.getMessage());
        } finally {
            sigFile.delete();
        }
    }
    
    @Test
    public void testNoProfileRunWithInvalidSignatureFileAndExistingResource() throws Exception {
        
        File sigFile = new File("sigFile");
        sigFile.createNewFile();
        command.setSignatureFile(sigFile.getAbsolutePath());
        
        File resource = new File("resource");
        resource.mkdir();
        command.setResources(new String[] {
            resource.getAbsolutePath()
        });
        try {
            command.execute();
            fail("Expected CommandExecutionException");
        } catch (CommandExecutionException x) {
            assertEquals("Resources directory not found", x.getMessage());
        } finally {
            sigFile.delete();
            resource.delete();
        }
    }
    
    @Ignore
    @Test
    public void testNoProfileRunWithExistingSignatureFileAndResource() throws Exception {
        
        File sigFile = new File("foo.droid/DROID_SignatureFile_V32.xml");
        command.setSignatureFile("sigFile");
        command.setResources(new String[] {
            "test1.txt",
            "test2.txt",
        });
        try {
            command.execute();
            fail("Expected CommandExecutionException");
        } catch (CommandExecutionException x) {
            assertEquals("Resources directory not found", x.getMessage());
        }
        
/*        SignatureFileInfo sig = mock(SignatureFileInfo.class);
        Map<SignatureType, SignatureFileInfo> sigs = new HashMap<SignatureType, SignatureFileInfo>();
        sigs.put(SignatureType.BINARY, sig);
        
        when(signatureManager.getDefaultSignatures()).thenReturn(sigs);
        
        ProfileInstance profileInstance = mock(ProfileInstance.class);
        when(profileInstance.getUuid()).thenReturn("abcde");
        when(profileManager.createProfile(sigs)).thenReturn(profileInstance);
        
        Future future = mock(Future.class);
        when(profileManager.start("abcde")).thenReturn(future);
        
        FileProfileResource resource1 = new FileProfileResource(new File("test1.txt"));
        FileProfileResource resource2 = new FileProfileResource(new File("test2.txt"));
        
        when(locationResolver.getResource("test1.txt", false)).thenReturn(resource1);
        when(locationResolver.getResource("test2.txt", false)).thenReturn(resource2);
        
        command.execute();
        
        verify(profileInstance).addResource(resource1);
        verify(profileInstance).addResource(resource2);
        verify(profileManager).createProfile(sigs);
        verify(profileManager).start("abcde");
        verify(future).get();
        verify(profileManager).save(eq("abcde"), eq(new File("test")), any(ProgressObserver.class));
        verify(profileManager).closeProfile("abcde"); */
    }
    
}