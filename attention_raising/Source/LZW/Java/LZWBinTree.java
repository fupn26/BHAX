// z3a7.cpp
//
// Copyright (C) 2011, 2012, Bátfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

public class LZWBinTree
{
  public LZWBinTree ()
  {
      tree = root;
  }
  
//************Helyettesíteni kell valamivel**************
//   ~LZWBinTree ()
//  {
//    free_mem (root.one_child ());
//    free_mem (root.zero_child ());
//  }

//  void operator<< (char b)
//  {
//    if (b == '0')
//      {
//	if (!tree->zero_child ())
//	  {
//	    Node *new_node = new Node ('0');
//	    tree->new_zero_child (new_node);
//	    tree = &root;
//	  }
//	else
//	  {
//	    tree = tree->zero_child ();
//	  }
//      }
//    else
//      {
//	if (!tree->one_child ())
//	  {
//	    Node *new_node = new Node ('1');
//	    tree->new_one_child (new_node);
//	    tree = &root;
//	  }
//	else
//	  {
//	    tree = tree->one_child ();
//	  }
//      }
//  }

  public void print_out (void)
  {
    depth = 0;
    print_out (&root, std::cout);
  }

  friend std::ostream & operator<< (std::ostream & os, LZWBinTree & bf)
  {
    bf.print_out (os);
    return os;
  }
  void print_out (std::ostream & os)
  {
    depth = 0;
    print_out (&root, os);
  }

int
getDepth (void)
{
  depth = maxDepth = 0;
  rdepth (&root);
  return maxDepth - 1;
}

double
getAverage (void)
{
  depth = average_sum = average_pcs = 0;
  raverage (&root);
  average = ((double) average_sum) / average_pcs;
  return average;
}

double
getDeviance (void)
{
  average = getAverage ();
  deviance_sum = 0.0;
  depth = average_sum = 0;

  rdeviance (&root);

  if (average_pcs - 1 > 0)
    deviance = std::sqrt (deviance_sum / (average_pcs - 1));
  else
    deviance = std::sqrt (deviance_sum);

  return deviance;
}



  private class Node
  {

   public Node (char b = '/')
    {
        character  = b;
        left_zero = null;
        right_one = null;
    };

//    ~Node ()
//    {
//    };

    public final Node zero_child ()
    {
      return left_zero;
    }

    public final Node one_child ()
    {
      return right_one;
    }

    public void new_zero_child (Node child)
    {
      left_zero = child;
    }

    public void new_one_child (Node child)
    {
      right_one = child;
    }

    public final char getCharacter ()
    {
      return character;
    }


    private char character;

    private Node left_zero;
    private Node right_one;

//    private Node (const Node &);
//   private Node & operator= (const Node &);
  }

  private Node *tree;

  private int depth, average_sum, average_pcs;
  private double deviance_sum;

  private LZWBinTree (const LZWBinTree &);
  private LZWBinTree & operator= (const LZWBinTree &);

  private void print_out (Node * element, std::ostream & os)
  {
    if (element != NULL)
      {
	++depth;

	print_out (element->one_child (), os);

	for (int i = 0; i < depth; ++i)
	  os << "---";

	os << element->getCharacter () << "(" << depth - 1 << ")" << std::endl;

	print_out (element->zero_child (), os);

	--depth;
      }
  }

//  void free_mem (Node * element)
//  {
//    if (element != NULL)
//      {
//	free_mem (element->one_child ());
//	free_mem (element->zero_child ());
//	delete element;
//      }
//  }


  protected Node root;
  protected int maxDepth;
  protected double average, deviance;

protected void
rdepth (Node * element)
{
  if (element != NULL)
    {
      ++depth;
      if (depth > maxDepth)
  maxDepth = depth;
      rdepth (element->one_child ());
      rdepth (element->zero_child ());
      --depth;
    }
}

protected void
raverage (Node * element)
{
  if (element != NULL)
    {
      ++depth;
      raverage (element->one_child());
      raverage (element->zero_child ());
      --depth;
      if (element->one_child () == NULL && element->zero_child() == NULL)
  {
    ++average_pcs;
    average_sum += depth;
  }
    }
}

protected void
rdeviance (Node * element)
{
  if (element != NULL)
    {
      ++depth;
      rdeviance (element->one_child ());
      rdeviance (element->zero_child ());
      --depth;
      if (element->one_child () == NULL && element->zero_child () == NULL)
  {
    ++average_pcs;
    deviance_sum += ((depth - average) * (depth - average));
  }
    }
  }


public static void usage ()
{
  std::cout << "Usage: lzwtree in_file -o out_file" << std::endl;
}


public static void main (String[] args)
{

  if (argc != 4)
    {
      usage ();
      return -1;
    }

  char *inFile = *++argv;

  if (*((*++argv) + 1) != 'o')
    {
      usage ();
      return -2;
    }

  std::fstream inputFile (inFile, std::ios_base::in);

  if (!inputFile)
    {
      std::cout << inFile << " doesn't exist..." << std::endl;
      usage ();
      return -3;
    }

  std::fstream outputFile (*++argv, std::ios_base::out);

  unsigned char b;
  LZWBinTree binTree;

  while (inputFile.read ((char *) &b, sizeof (unsigned char)))
    if (b == 0x0a)
      break;

  bool inComment = false;

  while (inputFile.read ((char *) &b, sizeof (unsigned char)))
    {

      if (b == 0x3e)
	{
	  inComment = true;
	  continue;
	}

      if (b == 0x0a)
	{
	  inComment = false;
	  continue;
	}

      if (inComment)
	continue;

      if (b == 0x4e)
	continue;

  for (int i = 0; i < 8; ++i)
	{
	  if (b & 0x80)
	    binTree << '1';
	  else
	    binTree << '0';
	  b <<= 1;
	}
    }

  outputFile << binTree;

  outputFile << "depth = " << binTree.getDepth () << std::endl;
  outputFile << "mean = " << binTree.getAverage () << std::endl;
  outputFile << "var = " << binTree.getDeviance () << std::endl;

  outputFile.close ();
  inputFile.close ();

  return 0;
}
}
