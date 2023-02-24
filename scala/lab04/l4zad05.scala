type Pred[A] = A => Boolean

def and[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    (n: A) => p(n) && q(n)
}

def or[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    (n: A) => p(n) || q(n)
}

def not[A](p: Pred[A]): Pred[A] = {
    (n: A) => !p(n)
}

def imp[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    (n: A) => !p(n) || q(n)
}
