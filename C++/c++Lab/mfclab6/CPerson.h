#pragma once

class CPerson : public CObject  
{  
    DECLARE_SERIAL(CPerson)  //---1
public:  
    CPerson(void);  //4
    CPerson(CString name, int age, bool gender = true);  //5
      
    virtual void Serialize(CArchive& ar);  //3
    virtual ~CPerson(void); 

	 CString m_name;  
    int     m_age;  
    bool    m_gender;  
private:  
   
    CString m_words;  
};  
