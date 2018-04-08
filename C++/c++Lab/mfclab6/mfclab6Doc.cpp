// mfclab6Doc.cpp : implementation of the Cmfclab6Doc class
//

#include "stdafx.h"
#include "mfclab6.h"

#include "mfclab6Doc.h"

#include "CPerson.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfclab6Doc

IMPLEMENT_DYNCREATE(Cmfclab6Doc, CDocument)

BEGIN_MESSAGE_MAP(Cmfclab6Doc, CDocument)
END_MESSAGE_MAP()


// Cmfclab6Doc construction/destruction

Cmfclab6Doc::Cmfclab6Doc()
{
	// TODO: add one-time construction code here

}

Cmfclab6Doc::~Cmfclab6Doc()
{
}

//BOOL Cmfclab6Doc::OnOpenDocument(LPCTSTR pathName){
//return TRUE;
//}

BOOL Cmfclab6Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// Cmfclab6Doc serialization

void Cmfclab6Doc::Serialize(CArchive& ar)
{
	//AfxMessageBox(ar.GetFile()->GetFileName());
	//setlocale(LC_ALL, "chs");
	if (ar.IsStoring())
	{
		CPerson p(_T("bbb"),1,true);
		// TODO: add storing code here
		ar<<&p;
	}
	else
	{
		CPerson* p2;
		ar>>p2;

		AfxMessageBox(p2->m_name);
		// TODO: add loading code here
	}
}




// Cmfclab6Doc diagnostics

#ifdef _DEBUG
void Cmfclab6Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void Cmfclab6Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// Cmfclab6Doc commands
