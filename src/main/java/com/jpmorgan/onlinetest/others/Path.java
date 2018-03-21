package com.jpmorgan.onlinetest.others;
/*
Write a function that provides change directory (cd) function for an abstract file system.

Notes:

Root path is '/'.
Path separator is '/'.
Parent directory is addressable as "..".
Directory names consist only of English alphabet letters (A-Z and a-z).
The function should support both relative and absolute paths.
The function will not be passed any invalid paths.
Do not use built-in path-related functions.
For example:

Path path = new Path("/a/b/c/d");
path.cd('../x');
System.out.println(path.getPath());
should display '/a/b/c/x'.
 */


import java.util.Arrays;
import java.util.LinkedList;

public class Path {
    private String path;

    public Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void cd(String newPath) {
        //throw new UnsupportedOperationException("Waiting to be implemented.");
        String[] ps=path.split("/");
        LinkedList<String> psl= new LinkedList();
        psl.addAll(Arrays.asList(ps));
        String[] nps=newPath.split("/");

        for (String np : nps) {
            if(np.equalsIgnoreCase("..")) psl.removeLast();
            else psl.addLast(np);
        }

        StringBuffer bf=new StringBuffer("");
        for (String o : psl) {
            if(o!=null&&!"".equals(o))bf.append("/").append(o);
        }

        path=bf.toString();

    }

    public static void main(String[] args) {
        Path path = new Path("/a/b/c/d");
        path.cd("../x");
        System.out.println(path.getPath());
    }
}
