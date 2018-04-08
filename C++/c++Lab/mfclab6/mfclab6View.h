// mfclab6View.h : interface of the Cmfclab6View class
//


#pragma once


class Cmfclab6View : public CView
{
protected: // create from serialization only
	Cmfclab6View();
	DECLARE_DYNCREATE(Cmfclab6View)

// Attributes
public:
	Cmfclab6Doc* GetDocument() const;
	CPoint prevPoint;
	CPen* pen;
	bool isDrawing;
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
	virtual ~Cmfclab6View();

#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
afx_msg void OnLButtonDown(UINT flags,CPoint p);
afx_msg void OnLButtonUp(UINT flags,CPoint p);
afx_msg void OnMouseMove(UINT flags,CPoint p);
// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnViewHi();
	afx_msg void OnUpdateViewHi(CCmdUI *pCmdUI);
	afx_msg void OnAC();
	afx_msg void OnUpdateAB(CCmdUI *pCmdUI);
	afx_msg void OnAD();
};

#ifndef _DEBUG  // debug version in mfclab6View.cpp
inline Cmfclab6Doc* Cmfclab6View::GetDocument() const
   { return reinterpret_cast<Cmfclab6Doc*>(m_pDocument); }
#endif

