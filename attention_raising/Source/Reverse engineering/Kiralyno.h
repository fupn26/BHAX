
#ifndef KIRALYNO_H
#define KIRALYNO_H

#include string

/**
  * class Kiralyno
  * 
  */

class Kiralyno
{
public:

  // Constructors/Destructors
  //  


  /**
   * Empty Constructor
   */
  Kiralyno ();

  /**
   * Empty Destructor
   */
  virtual ~Kiralyno ();

  // Static Public attributes
  //  

  // Public attributes
  //  


  // Public attribute accessor methods
  //  


  // Public attribute accessor methods
  //  



  /**
   * @return int
   */
  int getX ()
  {
  }


  /**
   * @return int
   */
  int getY ()
  {
  }


  /**
   * @param  X
   * @param  Y
   */
  void lepesValidator (int X, int Y)
  {
  }

protected:

  // Static Protected attributes
  //  

  // Protected attributes
  //  

public:


  // Protected attribute accessor methods
  //  

protected:

public:


  // Protected attribute accessor methods
  //  

protected:


private:

  // Static Private attributes
  //  

  // Private attributes
  //  

  int m_pozicioY;
  int m_pozicioX;
public:


  // Private attribute accessor methods
  //  

private:

public:


  // Private attribute accessor methods
  //  


  /**
   * Set the value of m_pozicioY
   * @param new_var the new value of m_pozicioY
   */
  void setM_pozicioY (int new_var)   {
      m_pozicioY = new_var;
  }

  /**
   * Get the value of m_pozicioY
   * @return the value of m_pozicioY
   */
  int getM_pozicioY ()   {
    return m_pozicioY;
  }

  /**
   * Set the value of m_pozicioX
   * @param new_var the new value of m_pozicioX
   */
  void setM_pozicioX (int new_var)   {
      m_pozicioX = new_var;
  }

  /**
   * Get the value of m_pozicioX
   * @return the value of m_pozicioX
   */
  int getM_pozicioX ()   {
    return m_pozicioX;
  }
private:


  void initAttributes () ;

};

#endif // KIRALYNO_H
