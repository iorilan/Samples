// mfc_lab5View.h : interface of the Cmfc_lab5View class
//


#pragma once


class Cmfc_lab5View : public CView
{
protected: // create from serialization only
	Cmfc_lab5View();
	DECLARE_DYNCREATE(Cmfc_lab5View)

// Attributes
public:
	Cmfc_lab5Doc* GetDocument() const;

// Operations
public:

// Overrides
public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// Implementation
public:
	virtual ~Cmfc_lab5View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in mfc_lab5View.cpp
inline Cmfc_lab5Doc* Cmfc_lab5View::GetDocument() const
   { return reinterpret_cast<Cmfc_lab5Doc*>(m_pDocument); }
#endif

