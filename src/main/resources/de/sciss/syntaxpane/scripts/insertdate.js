// Import the needed java packages and classes
var version = java.lang.System.getProperty("java.version");
if (version.startsWith("1.8.0")) {
    load("nashorn:mozilla_compat.js");
}
importPackage(java.util);
importClass(javax.swing.JOptionPane)

function putDate() {
  TARGET.replaceSelection("This is a dummy proc that inserts the Current Date:\n" + new Date());
  TARGET.replaceSelection("\nTab Size of doc = " + AU.getTabSize(TARGET));
}