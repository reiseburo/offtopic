package offtopic.errors

import groovy.transform.InheritConstructors
import groovy.transform.CompileStatic

@CompileStatic /* See note below */
@InheritConstructors
class ConfigurationError extends Exception {
}

/*
https://jira.codehaus.org/browse/GROOVY-6951

Expected exception offtopic.errors.ConfigurationError, but got java.lang.VerifyError
    at org.spockframework.lang.SpecInternals.thrownImpl(SpecInternals.java:79)
    at offtopic.CuratorClientInitializationSpec.with an empty String(CuratorClientSpec.groovy:21)
Caused by: java.lang.VerifyError: Bad <init> method call from inside of a branch
Exception Details:
  Location:
    offtopic/errors/ConfigurationError.<init>(Ljava/lang/String;)V @87: invokespecial
  Reason:
    Error exists in the bytecode
  Bytecode:
    0000000: b800 164d 04bd 0029 5903 2b53 5910 ff12
    0000010: 04b8 002f 2a5f ab00 0000 00af 0000 0005
    0000020: 8794 83a0 0000 0032 aad3 b1ff 0000 0047
    0000030: c783 a456 0000 005a f0c1 c756 0000 0087
    0000040: 0000 9b75 0000 00a6 5f5a 5903 3212 31b8
    0000050: 0035 c000 315f 57b7 0037 a700 755f 5a59
    0000060: 0332 b800 3dc0 003f 5f57 b700 42a7 0062
    0000070: 5f5a 5903 32b8 003d c000 3f5f 5904 3212
    0000080: 31b8 0035 c000 315f 5905 32b8 0048 5f59
    0000090: 0632 b800 485f 57b7 0018 a700 355f 5a59
    00000a0: 0332 b800 3dc0 003f 5f59 0432 1231 b800
    00000b0: 35c0 0031 5f57 b700 4ba7 0016 5f5a 57b7
    00000c0: 004e a700 0dbb 0050 5912 52b7 0053 bf57
    00000d0: 2ab6 001c 4e2d 2a5f b500 1e2d 57b1     
  Stackmap Table:
    full_frame(@72,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@93,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@112,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@157,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@188,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@197,{UninitializedThis,Object[#63],Object[#85]},{Object[#87],UninitializedThis})
    full_frame(@207,{Object[#2],Object[#63],Object[#85]},{Object[#87]})

    at java.lang.Class.privateGetDeclaredConstructors(Class.java:2658)
    at java.lang.Class.getDeclaredConstructors(Class.java:2007)
    at org.codehaus.groovy.util.LazyReference.getLocked(LazyReference.java:46)
    at org.codehaus.groovy.util.LazyReference.get(LazyReference.java:33)
    at offtopic.CuratorClient.<init>(CuratorClient.groovy:10)
    at offtopic.CuratorClientInitializationSpec.with an empty String(CuratorClientSpec.groovy:18)
*/
