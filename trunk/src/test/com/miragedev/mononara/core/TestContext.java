package com.miragedev.mononara.core;

import junit.framework.TestCase;

public class TestContext extends TestCase
{

  public TestContext(String arg0)
  {
    super(arg0);
  }

  protected void setUp() throws Exception
  {
    super.setUp();
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }

  /*
   * Test method for 'com.miragedev.mononara.core.Context.indexOf(String, int)'
   */
  public void testIndexOf()
  {
    // TODO Auto-generated method stub

  }

  /*
   * Test method for 'com.miragedev.mononara.core.Context.isTheSame(int, String)'
   */
  public void testIsTheSame()
  {
    Context context = new Context();
    context.addKnowledge("a");
    context.addSpelling("a");
    assertTrue("Same case", context.isTheSame(0, "a"));
    assertTrue("Ignore case", context.isTheSame(0, "A"));
    assertFalse("Different spelling", context.isTheSame(0, "b"));
  }

}
