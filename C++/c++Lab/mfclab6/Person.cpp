#include "StdAfx.h"
#include "CPerson.h"

CPerson::CPerson(void)  
{  
    m_name = _T("hi");  
    m_age = 0;  
    m_gender = true;  
}

CPerson::CPerson(CString name, int age, bool gender)  
{  
    m_name = name;  
    m_age = age;  
    m_gender = gender;  
}  

void CPerson::Serialize(CArchive& ar)  
{  
    if (ar.IsStoring())  
    {  
        ar << this->m_name << this->m_age << this->m_gender << this->m_words;  
    }  
    else  
    {  
        ar >> this->m_name >> this->m_age >> this->m_gender >> this->m_words;  
    }  
}  
CPerson::~CPerson(void)  
{  
}  
IMPLEMENT_SERIAL(CPerson, CObject, 1)  //2
