public class Tour {
    private Node start;

    private class Node {
        private Point p;
        private Node next;
    }

    public Tour() {
        start = null;
    }

    public Tour(Point a, Point b, Point c, Point d) {
        start = new Node();
        start.p = a;
        Node second = new Node();
        second.p = b;
        Node third = new Node();
        third.p = c;
        Node fourth = new Node();
        fourth.p = d;
        start.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = start;
    }

    public int size() {
        Node current = start;
        if (start == null) return 0;
        int count = 0;
        do {
            count++;
            current = current.next;
        } while (!current.equals(start));
        return count;
    }

    public double length() {
        Node current = start;
        double length = 0.0;
        if (start == null) return length;
        do {
            length += current.p.distanceTo(current.next.p);
            current = current.next;
        } while (!current.equals(start));
        return length;
    }

    public String toString() {
        StringBuilder t = new StringBuilder();
        Node current = start;
        if (start == null) return "";
        else {
            do {
                t.append(current.p);
                t.append("\n");
                current = current.next;
            } while (!current.equals(start));
            return t.toString();
        }
    }

    public void draw() {
        Node current = start;
        if (start != null) {
            do {
                current.p.drawTo(current.next.p);
                current = current.next;
            } while (!current.equals(start));
        }
    }


    public void insertNearest(Point p) {
        if (start == null) {
            Node newP = new Node();
            newP.p = p;
            newP.next = newP;
            start = newP;
        }
        else {
            double min = p.distanceTo(start.p);
            Node current = start;
            Node best = start;
            do {
                double distance = p.distanceTo(current.p);
                if (distance < min) {
                    min = distance;
                    best = current;
                }
                current = current.next;
            } while (!current.equals(start));
            Node newPoint = new Node();
            newPoint.p = p;
            newPoint.next = best.next;
            best.next = newPoint;
        }

    }

    public void insertSmallest(Point p) {
        if (start == null) {
            Node newP = new Node();
            newP.p = p;
            newP.next = newP;
            start = newP;
        }
        else {
            Node newP = new Node();
            newP.p = p;
            Node current = start;
            Node best = start;
            newP.next = start.next;
            start.next = newP;
            double initial = start.p.distanceTo(start.next.next.p);
            double newL = start.p.distanceTo(start.next.p) + start.next.p
                    .distanceTo(start.next.next.p);
            double deltaL = newL - initial;
            do {
                current.next = current.next.next;
                current = current.next;
                newP.next = current.next;
                current.next = newP;
                initial = current.p.distanceTo(current.next.next.p);
                newL = current.p.distanceTo(current.next.p) + current.next.p
                        .distanceTo(current.next.next.p);
                double newDelta = newL - initial;
                if (newDelta < deltaL) {
                    deltaL = newDelta;
                    best = current;
                }
            } while (!current.equals(start));
            current.next = current.next.next;
            newP.next = best.next;
            best.next = newP;
        }
    }

    public static void main(String[] args) {

        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);

        Tour squareTour = new Tour(a, b, c, d);

        Tour emptyTour = new Tour();

        int size = squareTour.size();
        StdOut.println("# of points = " + size);

        double length = squareTour.length();
        StdOut.println("Tour length = " + length);

        StdOut.println(squareTour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        squareTour.draw();
        emptyTour.draw();


        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        Point e = new Point(5.0, 6.0);
        squareTour.insertNearest(e);
        squareTour.draw();
        emptyTour.insertNearest(e);
        emptyTour.draw();
        StdOut.println(squareTour);
        StdOut.println(emptyTour);
    }
}
