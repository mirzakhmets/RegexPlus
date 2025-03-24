package com.regexplus;

import com.regexplus.automaton.common.IState;
import com.regexplus.automaton.dfa.DeterministicAutomaton;
import com.regexplus.automaton.model.Automaton;
import com.regexplus.automaton.model.StringStream;
import com.regexplus.match.common.IMatch;
import com.regexplus.parser.Parser;
import com.regexplus.parser.node.common.INode;
import com.regexplus.parser.node.model.Node;
import com.regexplus.test.Case;
import com.regexplus.test.CaseResult;

import java.io.*;
import java.util.List;

public class Main {
    public static boolean DETERMINISTIC = true;

    static void testOne() {
        String text = "abcd"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("testOne-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("testOne-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTwo() {
        new Case("a+bc?d*|efgh", new CaseResult[]{
                new CaseResult("aaab", 4),
                new CaseResult("aabc", 4),
                new CaseResult("abcd", 4),
                new CaseResult("efgh", 4),
                new CaseResult("cad", -1),
                new CaseResult("ab", 2),
                new CaseResult("abcddddd", 8)
        }).test();
    }

    static void testTwoA() {
        new Case("(abcdef|defg)-defg", new CaseResult[]{
                new CaseResult("abcdef", 6)
        }).test();
    }

    static void testThree() {
        String text = "(a*|aa)&aa"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testThree-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testThree-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testFour() {
        new Case("a*&a+", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("a", 1)
        }).test();
    }

    static void testFive() {
        String text = "a*-aa"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testFive-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testFive-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testSix() {
        new Case("a*-aa", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("aa", 1),
                new CaseResult("aaaa", 4)
        }).test();
        new Case("(Hello|world)-world", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("world", -1),
                new CaseResult("Hello", 5)
        }).test();
    }

    static void testSeven() {
        String text = ".*"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testSeven-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testSeven-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testEight() {
        new Case(".*", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("aa", 2),
                new CaseResult("aaa", 3)
        }).test();
    }

    static void testNine() {
        String text = "~(a|b)"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testNine-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testNine-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTen() {
        new Case("~(a|b)|c", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("b", 0),
                new CaseResult("a", 0),
                new CaseResult("c", 1)
        }).test();
    }

    static void testEleven() {
        String text = "[ab]*"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testEleven-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/testEleven-2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testTwelfth() {
        new Case("[ab]*", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("b", 1),
                new CaseResult("a", 1),
                new CaseResult("abab", 4),
                new CaseResult("c", 0)
        }).test();
    }

    static void testThirteenth() {
        new Case("~(ab-cd)", new CaseResult[]{
                //new CaseResult("aa", 2),
                new CaseResult("ab", 1),
                new CaseResult("cd", 2),
                new CaseResult("abcd", 4)
        }).test();
    }

    static void testFourteenth() {
        // due to S.M. Kearns
        new Case("(ab|abb)(ba&a)", new CaseResult[]{
                new CaseResult("abba", -1)
        }).test();
    }

    static void testFifteenth() {
        // due to S.M. Kearns
        new Case("a|b", new CaseResult[]{
                new CaseResult("a", 1),
                new CaseResult("b", 1),
                new CaseResult("", -1)
        }).test();
    }

    static void testSixteenth() {
        // due to S.M. Kearns
        new Case("(a&a)(ab|abb)", new CaseResult[]{
                new CaseResult("aabb", 4),
                new CaseResult("aab", 3),
                new CaseResult("ab", -1)
        }).test();
    }

    static void testSeventeenth() {
        // due to S.M. Kearns
        new Case("(a&a)(ab|abb)", new CaseResult[]{
                new CaseResult("aab", 3)
        }).test();
    }

    static void testEighteenth() {
        // due to S.M. Kearns
        new Case("((a|aa)&(a+))((aa)-a)", new CaseResult[]{
                new CaseResult("aa", -1),
                new CaseResult("aaaa", 4),
                new CaseResult("aaa", 3)
        }).test();
    }

    static void testNineteenth() {
        // due to S.M. Kearns
        new Case("(a(a&ab)|c)", new CaseResult[]{
                new CaseResult("c", 1),
                new CaseResult("aab", -1)
        }).test();
    }

    static void testTwenty() {
        // due to S.M. Kearns
        new Case("a(a+&a*)", new CaseResult[]{
                new CaseResult("aa", 2),
                new CaseResult("a", -1)
        }).test();
    }

    static void testTwentyOne() {
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(1000));
        new Case("a*(a+&a*)+", new CaseResult[]{
                new CaseResult(sb.toString(), sb.length())
        }).test();
    }

    static void testTwentyTwo() {
        new Case("(b(a+&a*)+c)&(.*)", new CaseResult[]{
                new CaseResult("baac", 4),
                new CaseResult("bac", 3),
                new CaseResult("b", -1)
        }).test();
    }

    static void testTwentyThree() {
        new Case("aaa-a*", new CaseResult[]{
                new CaseResult("", -1),
                new CaseResult("a", -1),
                new CaseResult("aa", -1),
                new CaseResult("aaa", -1)
        }).test();
    }

    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int c;
            while ((c = br.read()) != -1) {
                content.append((char) c);
            }
            br.close();
        } catch (Exception e) {
            return null;
        }
        return content.toString();
    }

    static void test() {
        String text = "~(a|b)"; //"(Hel+lo*)?|, Regex((Pl|us)!)";
        INode node = Parser.ParseFromString(text);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("C:/Debug/test-1.gv")));
            Case.writeNode(bw, node);
            bw.close();
            bw = new BufferedWriter(new FileWriter(new File("C:/Debug/test2.gv")));
            IState[] start = new IState[1];
            IState[] finish = new IState[1];
            ((Node) node).expand(start, finish);
            Case.writeState(bw, start[0]);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRegistered() {
        try {
            File file = new File(System.getProperty("user.home") + "/register.lic");

            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void checkRuns() {
        try {
            File file = new File(System.getProperty("user.home") + "/run.lic");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileInputStream fis = new FileInputStream(new File(System.getProperty("user.home") + "/run.lic"));

            byte[] b = fis.readAllBytes();

            if (b.length > 30) {
                System.out.println("Number of runs expired. Please purchase the program (visit site https://ovg-developers.mystrikingly.com/).");
                System.exit(0);
            }

            fis.close();

            FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.home") + "/run.lic"));

            fos.write(b);

            fos.write(' ');

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void MertensTest(int na, int nb) {
        // Mertens' tests
        long t = System.currentTimeMillis();
        Automaton automaton = new Automaton();
        String part1 = "", part11 = "", part12 = "";
        String part2 = "", part21 = "", part22 = "";

        for (int i = 0; i < na; ++i) {
            part11 += "a";
        }

        part11 = "(" + part11 + ")*";

        for (int i = 0; i < nb; ++i) {
            part21 += "a";
        }

        part21 = "(" + part21 + ")*";

        for (int i = 0; i < na - 1; ++i) {
            part12 += "a";

            if (part1.length() > 0) {
                part1 += "|";
            }

            part1 += part11 + part12;

            //if (part12.length() >= 4) {
            //    part12 = "";
            //}
        }

        for (int i = 0; i < nb; ++i) {
            part22 += "a";

            if (part22.length() != 1) {
                if (part2.length() > 0) {
                    part2 += "|";
                }

                part2 += part22 + part21;
            }

            //if (part22.length() >= 3) {
            //    part22 = "";
            //}
        }

        String pattern = "(a|aa|aaa|" + part2 + ")-" + "(" + part1 + ")";

        System.out.println(pattern);

        System.out.println(pattern.length());

        automaton.build(new StringStream(pattern));

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("pattern.gv")));
            Case.writeState(bw, automaton.getStart());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeterministicAutomaton deterministicAutomaton = new
                DeterministicAutomaton(automaton);

        System.out.println(deterministicAutomaton.states.size());

        try {
            FileOutputStream fos = new
                    FileOutputStream("dfa.gv");
            deterministicAutomaton.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println((System.currentTimeMillis() - t) / 1e+3);
    }

    public static void MertensTest2(int na) {
        // Mertens' tests
        long t = System.currentTimeMillis();
        Automaton automaton = new Automaton();
        String part1 = "";

        for (int i = 0; i < na; ++i) {
            part1 += "a";
        }

        part1 = "(" + part1 + ")*a|(" + part1 + ")*aa";

        String pattern = "(" + part1 + ")-((aa)*)";

        System.out.println(pattern);

        System.out.println(pattern.length());

        automaton.build(new StringStream(pattern));

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("pattern.gv")));
            Case.writeState(bw, automaton.getStart());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeterministicAutomaton deterministicAutomaton = new
                DeterministicAutomaton(automaton);

        System.out.println(deterministicAutomaton.states.size());

        try {
            FileOutputStream fos = new
                    FileOutputStream("dfa.gv");
            deterministicAutomaton.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println((System.currentTimeMillis() - t) / 1e+3);
    }

    public static final String alphabet = "a";

    public static void ArbitraryTest1(int na, int nb) {
        long t = System.currentTimeMillis();
        Automaton automaton = new Automaton();
        String part1 = "", part11 = "", part12 = "";
        String part2 = "", part21 = "", part22 = "";

        for (int i = 0; i < na; ++i) {
            part11 += alphabet.charAt(i % alphabet.length()) + "*";
        }

        part11 = "(" + part11 + ")*";

        for (int i = 0; i < nb; ++i) {
            part21 += alphabet.charAt(i % alphabet.length()) + "*";
        }

        part21 = "(" + part21 + ")*";

        for (int i = 0; i < na - 1; ++i) {
            part12 += alphabet.charAt(i % alphabet.length()) + "*";

            if (part1.length() > 0) {
                part1 += "|";
            }

            part1 += part11 + part12;
        }

        for (int i = 0; i < nb; ++i) {
            part22 += alphabet.charAt(i % alphabet.length()) + "*";

            if (part22.length() != 1) {
                if (part2.length() > 0) {
                    part2 += "|";
                }

                part2 += part22 + part21;
            }
        }

        String pattern = "(" + part1 + ")-(a|aa|aaa|" + part2 + ")";

        System.out.println(pattern);

        System.out.println(pattern.length());

        automaton.build(new StringStream(pattern));

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new
                    File("pattern.gv")));
            Case.writeState(bw, automaton.getStart());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeterministicAutomaton deterministicAutomaton = new
                DeterministicAutomaton(automaton);

        System.out.println(deterministicAutomaton.states.size());

        try {
            FileOutputStream fos = new
                    FileOutputStream("dfa.gv");
            deterministicAutomaton.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println((System.currentTimeMillis() - t) / 1e+3);
    }

    public static void main(String[] args) {
        //if (!isRegistered()) {
        //    checkRuns();
        //}


 /*
 test();
 StringBuilder s = new StringBuilder();
 s.append("ab".repeat(1000000));
 new Case("~(a|b)", new CaseResult[] {
 new CaseResult("a", 0),
 new CaseResult("b", 0),
 new CaseResult("ab", 2),
 new CaseResult(s.toString(), s.length())
 }).test();
 */
 /*
 for (int i = 0; i < args.length; ++i) {
 if (args[i].equals("-d")) {
 Main.DETERMINISTIC = true;
 }
 }
 */
        //int[] cases = new int[] { 0, 10, 11, 12, 13, 20, 30};
        //int[] cases = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 20, 30, 40 };
        //int[] cases = new int[] { 0 };
        int[] cases = new int[] { 4000 };

        for (int i = 0; i < cases.length; ++i) {
            MertensTest2(cases[i]);

            //ArbitraryTest1(4 + cases[i], 3 + cases[i]);
        }

        if (args.length < 2) {
            System.out.println("Regex+ - Usage: <pattern> <file name>");
            System.out.println("Syntax:");
            System.out.println("R = ");
            System.out.println(" (A | [\"~\"] /* complement */ (R) | \".\" | \"[\" A* \"]\") [\"*\" | \"+\" | \"?\"],");
            System.out.println(" R1 R2,");
            System.out.println(" R1 | R2,");
            System.out.println(" R1 & R2 /* intersection */");
            System.out.println(" R1 - R2 /* subtraction */");

            /*
            testTwoA();
            testOne();
            testTwo();
            testThree();
            testFour();
            testFive();
            testSix();
            testSeven();
            testEight();
            testNine();
            testTen();
            testEleven();
            testTwelfth();
            testThirteenth();
            testFourteenth();
            testFifteenth();
            testSixteenth();
            testSeventeenth();
            testEighteenth();
            testNineteenth();
            testTwenty();
            testTwentyOne();
            testTwentyTwo();
            testTwentyThree();
            */

 /*
 long t = System.currentTimeMillis();
 Automaton automaton = new Automaton();
 automaton.build(new
StringStream("((1+0+1+1+0+0+0+0+1+1+1+0+0+|1+0+1+1+0+1+0+0+0+
1+0+1+1+|1+0+1+1+0+0+1+0+0+1+1+0+1+|1+0+1+0+0+1+0+1+0+1+0+
|1+0+1+0+0+1+0+0+1+0+1+|0+1+1+1+1+1+0+1+0+1+0+)+)&((1+0+0+1
+0+0+1+0+1+1+0+0+|0+1+1+0+1+1+1+0+1+0+0+|1+0+1+1+1+0+1+1+0
+0+1+0+|0+1+0+1+0+1+1+1+1+0+0+0+0+|1+0+0+0+1+0+1+1+1+0+0+|
1+1+1+0+1+1+0+0+1+1+0+1+)+)"));
 String s = "";
 long size = 1000;
 for (int i = 0; i < size; ++i)
 s += "101";
 if (automaton.matches(new StringStream(s))) {
 //List<IMatch> matches = automaton.match(new
StringStream(s));
 System.out.println("ok");
 //System.out.println(s.substring(matches.get(0).start(),
matches.get(0).start() + matches.get(0).length()));
 }
 System.out.println("Time for building automata: " +
(System.currentTimeMillis() - t) + "ms");
 */
            System.exit(0);
        }
        String pattern = args[args.length - 2];
        String string = readFile(args[args.length - 1]);
        Automaton automaton = new Automaton();
        automaton.build(new StringStream(pattern));
        if (Main.DETERMINISTIC) {
            DeterministicAutomaton deterministicAutomaton = new
                    DeterministicAutomaton(automaton);
            if (deterministicAutomaton.matches(new StringStream(string))) {
                List<IMatch> matches = deterministicAutomaton.match(new
                        StringStream(string));
                System.out.println(string.substring(matches.get(0).start(),
                        matches.get(0).start() + matches.get(0).length()));
                System.exit(0);
            } else {
                System.out.println("No match");
                System.exit(1);
            }
        } else {
            if (automaton.matches(new StringStream(string))) {
                List<IMatch> matches = automaton.match(new
                        StringStream(string));
                System.out.println(string.substring(matches.get(0).start(),
                        matches.get(0).start() + matches.get(0).length()));
                System.exit(0);
            } else {
                System.out.println("No match");
                System.exit(1);
            }
        }
    }
}
